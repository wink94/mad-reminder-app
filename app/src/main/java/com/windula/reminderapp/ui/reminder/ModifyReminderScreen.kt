package com.windula.reminderapp.ui.reminder

import android.Manifest
import android.app.PendingIntent
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Context.JOB_SCHEDULER_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddLocation
import androidx.compose.material.icons.filled.Mic
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.work.*
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.windula.core_domain.entity.Reminder
import com.windula.reminderapp.ui.Screens
import com.windula.reminderapp.ui.components.DatePicker
import com.windula.reminderapp.ui.components.PickImageFromGallery
import com.windula.reminderapp.ui.components.RepeatReminderDropdown
import com.windula.reminderapp.ui.components.TimePicker
import com.windula.reminderapp.ui.theme.*
import com.windula.reminderapp.util.*
import com.windula.reminderapp.util.geofence.GeofenceBroadcastReceiver
import com.windula.reminderapp.util.geofence.GeofenceJobService
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import java.util.concurrent.TimeUnit


//@Preview
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ModifyReminderScreen(
    navController: NavController,
    reminder: Reminder?,
    viewModel: ReminderViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    var title by remember { mutableStateOf(reminder?.title ?: "") }
    var message by remember { mutableStateOf(reminder?.message ?: "") }
    var date by remember { mutableStateOf(reminder?.reminderDate ?: LocalDate.now().toString()) }
    var time by remember { mutableStateOf(reminder?.reminderTime ?: "00:00") }
    var reminderRepeat by remember { mutableStateOf("") }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = {}
    )
    val voicePermissionState = rememberPermissionState(Manifest.permission.RECORD_AUDIO)
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scrollState = rememberScrollState()

    val geofencingClient = remember { LocationServices.getGeofencingClient(context) }

    val geofence = remember {Geofence.Builder()
        .setRequestId(GEOFENCE_ID)
        .setCircularRegion(6.816927810850897,
            79.86943492064688, GEOFENCE_RADIUS.toFloat())
        .setExpirationDuration(GEOFENCE_EXPIRATION.toLong())
        .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER or Geofence.GEOFENCE_TRANSITION_DWELL)
        .setLoiteringDelay(GEOFENCE_DWELL_DELAY)}

    var location: LatLng? by remember {
        mutableStateOf(
            LatLng(
                6.816927810850897,
                79.86943492064688
            )
        )
    }

    var latLng =
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<LatLng>("location_data")
            ?.observeAsState()

    latLng?.value?.let {
        location = it
        print(it)
        geofence.setCircularRegion(latLng.value!!.latitude,
            latLng.value!!.longitude, GEOFENCE_RADIUS.toFloat())
    }



    Box(
        modifier = Modifier
            .scrollable(state = scrollState, orientation = Orientation.Vertical)
    ) {


        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .padding(top = 60.dp)
                .verticalScroll(scrollState),

            ) {
            Image(
                painter = painterResource(id = com.windula.reminderapp.R.drawable.bell_notific_icon),
                contentDescription = "",
                modifier = Modifier.size(60.dp)
            )

            OutlinedTextField(
                value = title, onValueChange = {
                    title = it
                },
                label = {
                    Text(text = "Title", color = PrimaryColor)
                },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(top = 10.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = PrimaryColor,
                    textColor = Color.Black

                ),
                placeholder = {
                    Text(text = "Title", color = PlaceholderColor)
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType =
                    KeyboardType.Text
                ),
                singleLine = true,
                textStyle = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = Poppins
                )
            )

            OutlinedTextField(
                value = message, onValueChange = {
                    message = it
                },
                label = {
                    Text(text = "Message", color = PrimaryColor)
                },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(horizontal = 20.dp)
                    .padding(top = 10.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = PrimaryColor,
                    textColor = Color.Black
                ),
                placeholder = {
                    Text(text = "Message", color = PlaceholderColor)
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType =
                    KeyboardType.Text
                ),
                singleLine = false,
                textStyle = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = Poppins
                ),
                trailingIcon = {
                    IconButton(
                        interactionSource = interactionSource,
                        onClick = {}
                    )
                    {
                        Icon(Icons.Filled.Mic, null, tint = Color.Green)
                    }
                }

            )
            if (isPressed) {
                requestPermission(
                    context = context,
                    permission = Manifest.permission.RECORD_AUDIO,
                    requestPermission = { voicePermissionState.launchPermissionRequest() }
                ).apply {
                    startSpeechToText(vm = viewModel, context, finished = {})
                }

                DisposableEffect(Unit) {
                    onDispose {
                        //released
                        viewModel.textFromSpeech?.let {
                            message = it
                            Log.i(APP_TAG, it)
                        }
                    }
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp, top = 10.dp),
                backgroundColor = Color.White,
                elevation = 1.dp,
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(1.dp, PrimaryColor),

                ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                ) {
                    Text(
                        text = "Select Date and/or Time",
                        textAlign = TextAlign.Start,
                        fontFamily = Poppins,
                        color = PrimaryColor,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 14.dp, start = 5.dp),
                        fontWeight = FontWeight.Medium,
                        fontSize = 15.sp,
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(top = 50.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier
                            .padding(5.dp)

                    ) {

                        DatePicker(
                            label = "Date Picker",
                            value = date,
                            onValueChange = { date = it })
                        println(date)
                    }
                    Column(
                        horizontalAlignment = Alignment.End,
                        modifier = Modifier
                            .padding(5.dp)
                    ) {

                        TimePicker(
                            label = "Time Picker",
                            value = time,
                            onValueChange = { time = it })
                        println(time)
                    }
                }

            }

            RepeatReminderDropdown(value = reminderRepeat, onValueChange = { reminderRepeat = it })

            Button(
                onClick = {
                    requestPermission(
                        context = context,
                        permission = Manifest.permission.ACCESS_FINE_LOCATION,
                        requestPermission = {
                            launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                        }
                    ).apply {
                        requestPermission(
                            context = context,
                            permission = Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                            requestPermission = {
                                launcher.launch(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                            }
                        ).apply {
                            navController.navigate(Screens.GoogleMapComponent.route)
                        }

                    }
                    println(viewModel.imageUri)
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = PrimaryColor,
                ),
                elevation = ButtonDefaults.elevation(defaultElevation = 0.dp),
                modifier = Modifier
                    .clip(shape = Shapes.large)
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .padding(top = 5.dp),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.AddLocation, null)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Select Location ",
                        fontFamily = Poppins,
                        color = white
                    )
                }
            }

            PickImageFromGallery()

            Button(
                onClick = {

                    saveReminder(
                        navController, Reminder(
                            title = title,
                            message = message,
                            reminderTime = time,
                            reminderDate = date,
                            creationTime = LocalDateTime.now(),
                            creatorId = "admin",
                            reminderId = reminder?.reminderId,
                            image = viewModel?.imageUri,
                            reminderRepeat = reminderRepeat,
                            locationX = location!!?.longitude,
                            locationY = location!!?.latitude
                        ), viewModel, context,geofencingClient,geofence
                    )
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Purple500
                ),
                elevation = ButtonDefaults.elevation(defaultElevation = 0.dp),
                modifier = Modifier
                    .clip(shape = Shapes.large)
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .padding(top = 5.dp),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp),
                shape = Shapes.large
            ) {
                Text(
                    text = "Save",
                    fontFamily = Poppins,
                    color = white,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }


        }
    }
}

private fun saveReminder(
    navController: NavController,
    reminder: Reminder,
    viewModel: ReminderViewModel,
    context: Context, geofencingClient: GeofencingClient?, geofence: Geofence.Builder?
) {

    viewModel.saveReminder(
        reminder
    )

    if (geofencingClient!=null && geofence!=null){

        createGeoFence(context,reminder,geofencingClient,geofence)

        scheduleJob(context)
    }


    if (reminder.reminderDate != null && reminder.reminderTime != null) {
        val timeDelay = getTimeDelay(TimeUnit.MINUTES, reminder.reminderDate, reminder.reminderTime)

        if (timeDelay > 0) {

            if (reminder.reminderRepeat != "" && reminder.reminderRepeat != null) {
                println("reminder repeat " + reminder.reminderRepeat)
                val timeInterval = getTimeInterval(reminder.reminderRepeat!!, TimeUnit.MINUTES)
                createWorkRequest(
                    reminder.title,
                    reminder.message,
                    timeDelay,
                    context,
                    timeInterval
                )
            } else {
                createWorkRequest(reminder.title, reminder.message, timeDelay, context)
            }


            Log.i(APP_TAG, "reminder notification created")
        } else {
            Log.e(APP_TAG, "reminder notification failed")
            Toast.makeText(
                context,
                "Reminder notification not available for a past date",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    navController.navigate(Screens.Home.route)
}

private fun createGeoFence(context: Context,reminder: Reminder, geofencingClient: GeofencingClient,geofence: Geofence.Builder) {


    val geofenceRequest = GeofencingRequest.Builder()
        .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
        .addGeofence(geofence.build())
        .build()

    val geofenceIntent: PendingIntent by lazy {
        val intent = Intent(context, GeofenceBroadcastReceiver::class.java)
        intent.action = "ACTION_EVENT"
        intent.putExtra("key", reminder.title)
        intent.putExtra("message", " ${reminder.message}")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getBroadcast(
                context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        } else {
            PendingIntent.getBroadcast(
                context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }
    }

    println(geofenceRequest.geofences)
    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        // TODO: Consider calling
        //    ActivityCompat#requestPermissions
        // here to request the missing permissions, and then overriding
        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
        //                                          int[] grantResults)
        // to handle the case where the user grants the permission. See the documentation
        // for ActivityCompat#requestPermissions for more details.
        return
    }
    geofencingClient.addGeofences(geofenceRequest, geofenceIntent).run {
        addOnSuccessListener {
            Log.i(APP_TAG,"Geofence Added")
            Toast.makeText(
                context,
                "Geofence Added",
                Toast.LENGTH_SHORT
            ).show()
        }
        addOnFailureListener {
            print(it)
            Log.e(APP_TAG,"Geofence failed to add")
            Log.e(APP_TAG,it.toString())
            Toast.makeText(
                context,
                "Geofence Failed to Add",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


}

private fun createWorkRequest(
    title: String,
    message: String,
    timeDelayInSeconds: Long,
    context: Context,
    periodicTime: Long = 0
) {

    if (periodicTime > 0) {
        println("periodicTime $periodicTime")
        val myWorkRequest = PeriodicWorkRequestBuilder<ReminderWorker>(
            periodicTime, // repeating interval
            TimeUnit.MINUTES,
            timeDelayInSeconds, // flex interval - worker will run somewhen within this period of time, but at the end of repeating interval
            TimeUnit.MINUTES
        ).setInputData(
            workDataOf(
                "title" to title,
                "message" to message,
            )
        )
            .build()

        WorkManager.getInstance(context).enqueue(myWorkRequest)
    } else {
        val myWorkRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
            .setInitialDelay(timeDelayInSeconds, TimeUnit.MINUTES)
            .setInputData(
                workDataOf(
                    "title" to title,
                    "message" to message,
                )
            )
            .build()

        WorkManager.getInstance(context).enqueue(myWorkRequest)
    }
}

private fun requestPermission(
    context: Context,
    permission: String,
    requestPermission: () -> Unit
) {
    if (ContextCompat.checkSelfPermission(
            context,
            permission
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        requestPermission()
    }
    Log.i(APP_TAG, "permission granted")
}

private fun scheduleJob(context: Context) {
    val componentName = ComponentName(context, GeofenceJobService::class.java)
    val info = JobInfo.Builder(321, componentName)
        .setRequiresCharging(false)
        .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
        .setPersisted(true)
        .setPeriodic(15 * 60 * 1000)
        .build()

    val scheduler = context.getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
    val resultCode = scheduler.schedule(info)
    if (resultCode == JobScheduler.RESULT_SUCCESS) {
        Log.d(APP_TAG, "Job scheduled")
    } else {
        Log.d(APP_TAG, "Job scheduling failed")
        scheduleJob(context)
    }
}