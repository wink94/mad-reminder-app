package com.windula.reminderapp.ui.reminder

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddLocation
import androidx.compose.runtime.*
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
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.windula.core_domain.entity.Reminder
import com.windula.reminderapp.ui.Screens
import com.windula.reminderapp.ui.components.DatePicker
import com.windula.reminderapp.ui.components.TimePicker
import com.windula.reminderapp.ui.theme.*
import java.time.LocalDate
import java.time.LocalDateTime


//@Preview
@Composable
fun ModifyReminderScreen(
    navController: NavController,
    reminder: Reminder?,
    viewModel: ReminderViewModel = hiltViewModel()
) {
    var title by remember { mutableStateOf(reminder?.title?:"") }
    var message by remember { mutableStateOf(reminder?.message?:"") }
    var date by remember { mutableStateOf(reminder?.reminderDate?:LocalDate.now().toString()) }
    var time by remember { mutableStateOf(reminder?.reminderTime?:"00:00") }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = {}
    )


    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.padding(top = 10.dp)
    ) {
        Image(
            painter = painterResource(id = com.windula.reminderapp.R.drawable.bell_notific_icon),
            contentDescription = "",
            modifier = Modifier.size(80.dp)
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
                .height(120.dp)
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
            )
        )


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
                    .padding(bottom = 25.dp)
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

                    DatePicker(label = "Date Picker", value = date, onValueChange = { date = it })
                    println(date)
                }
                Column(
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier
                        .padding(5.dp)
                ) {

                    TimePicker(label = "Time Picker", value = time, onValueChange = { time = it })
                    println(time)
                }
            }

        }

        Button(
            onClick = {
                requestPermission(
                    context = context,
                    permission = Manifest.permission.ACCESS_FINE_LOCATION,
                    requestPermission = { launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION) }
                ).apply {
                    navController.navigate(Screens.GoogleMapComponent.route)
                }
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = PrimaryColor,
            ),
            elevation = ButtonDefaults.elevation(defaultElevation = 0.dp),
            modifier = Modifier
                .clip(shape = Shapes.large)
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(top = 5.dp),
            contentPadding = PaddingValues(horizontal = 26.dp, vertical = 10.dp)
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

        Button(
            onClick = {
                viewModel.saveReminder(
                    Reminder(
                        title = title,
                        message = message,
                        reminderTime = time,
                        reminderDate = date,
                        creationTime = LocalDateTime.now(),
                        creatorId = "admin",
                        reminderId = reminder?.reminderId
                    )
                )
                navController.navigate(Screens.Home.route)
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Purple500
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(top = 10.dp),
            contentPadding = PaddingValues(vertical = 5.dp),
            elevation = ButtonDefaults.elevation(
                defaultElevation = 0.dp,
                pressedElevation = 2.dp
            ),
            shape = Shapes.medium
        ) {
            Text(
                text = "Save",
                fontFamily = Poppins,
                color = white,
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }


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
}