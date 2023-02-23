package com.windula.reminderapp.ui.home

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.windula.core_domain.entity.Reminder
import com.windula.reminderapp.R
import com.windula.reminderapp.ui.Screens
import com.windula.reminderapp.ui.components.getPhotoFileUri
import com.windula.reminderapp.ui.reminder.ReminderViewModel
import com.windula.reminderapp.util.Constants.ReminderColorConstants
import java.time.LocalDate


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ReminderCard(
    reminder: Reminder,
    navController: NavController,
    viewModel: ReminderViewModel = hiltViewModel()
) {
    println(reminder)
    var expand by remember { mutableStateOf(false) } // Expand State
    val rotationState by animateFloatAsState(if (expand) 180f else 0f) // Rotation State
    var stroke by remember { mutableStateOf(1) } // Stroke State
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }
    val getColorMap = getCardColorSchemeByDate(reminder.reminderDate)
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .animateContentSize( // Animation
                animationSpec = tween(
                    durationMillis = 400, // Animation Speed
                    easing = LinearOutSlowInEasing // Animation Type
                )
            )
            .padding(8.dp),
        backgroundColor = getColorMap?.get("backGround")!!,
        shape = RoundedCornerShape(8.dp), // Shape
        border = BorderStroke(stroke.dp, getColorMap.get("text")!!), // Stroke Width and Color
        onClick = {
            expand = !expand
            stroke = if (expand) 2 else 1
        }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween // Control the header Alignment over here

            ) {
                Text(
                    modifier = Modifier
                        .padding(10.dp)
                        .drawBehind {
                            drawCircle(
                                color = getColorMap.get("dayCircleBackgroundColor")!!,
                                radius = this.size.maxDimension * 0.7f
                            )
                        },
                    text = getDateIcon(reminder.reminderDate),
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 22.sp,
                )

                Text(
                    text = reminder.title,
                    color = getColorMap.get("text")!!, // Header Color
                    fontSize = 20.sp,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier
                        .weight(.9f)
                        .padding(start = 20.dp, end = 20.dp)
                )

                IconButton(
                    modifier = Modifier
//                        .rotate(rotationState)
                        .weight(.1f)
                        .padding(end = 10.dp),
                    onClick = {
                        navController.navigate(
                            "reminder_screen/modify/{reminderId}"
                                .replace(
                                    oldValue = "{reminderId}",
                                    newValue = "${reminder.reminderId}"
                                )
                        )

                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        tint = Color.White,
                        contentDescription = "Edit reminder"
                    )
                }
                IconButton(
                    modifier = Modifier
//                        .rotate(rotationState)
                        .weight(.1f)
                        .padding(end = 10.dp),
                    onClick = {
                        viewModel.deleteReminder(reminder)
                        navController.navigate(Screens.Home.route)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        tint = Color.White,
                        contentDescription = "Delete reminder"
                    )
                }
                IconButton(
                    modifier = Modifier
                        .rotate(rotationState)
                        .weight(.1f)
                        .padding(end = 5.dp),
                    onClick = {
                        expand = !expand
                        stroke = if (expand) 2 else 1
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        tint = Color.White, // Icon Color
                        contentDescription = "Drop Down Arrow"
                    )
                }
            }
            if (expand) {

                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    Surface(
                        modifier = Modifier
                            .padding(5.dp)
                            .fillMaxWidth(),
                        color = Color.White.copy(alpha = 0.6F),
                        elevation = 8.dp,
                        shape = CircleShape,
                    ) {

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 20.dp)
                        ) {

                            Icon(
                                painter = painterResource(id = R.drawable.calendar),
                                contentDescription = "",
                                modifier = Modifier
                                    .size(20.dp)
                                    .align(Alignment.CenterVertically),
                                tint = Color.Unspecified
                            )


                            Text(
                                text = reminder.reminderDate,
                                color = getColorMap.get("text")!!, // date Color
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Normal,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp)
                            )


                        }
                    }

                    Surface(
                        modifier = Modifier
                            .padding(5.dp)
                            .fillMaxWidth(),
                        color = Color.White.copy(alpha = 0.6F),
                        elevation = 8.dp,
                        shape = CircleShape
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 20.dp)
                        ) {

                            Icon(
                                painter = painterResource(id = R.drawable.clock),
                                contentDescription = "",
                                modifier = Modifier
                                    .size(20.dp)
                                    .align(Alignment.CenterVertically),
                                tint = Color.Unspecified
                            )
                            Text(
                                text = reminder.reminderTime,
                                color = getColorMap.get("text")!!, // date Color
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Normal,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp)
                            )
                        }
                    }

                    Surface(
                        modifier = Modifier
                            .padding(5.dp)
                            .fillMaxWidth(),
                        color = Color.White.copy(alpha = 0.6F),
                        elevation = 8.dp,
                        shape = CircleShape
                    ) {

                        Text(
                            text = reminder.message,
                            color = getColorMap.get("text")!!, // Description Color
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                        )

                    }

                    if (reminder.image != null) {
                        Surface(
                            modifier = Modifier
                                .padding(5.dp)
                                .fillMaxWidth(),
                            color = Color.White.copy(alpha = 0.6F),
                            elevation = 8.dp,
                            shape = CircleShape
                        ) {

//                            val takenPhotoUri = Uri.fromFile(getPhotoFileUri(reminder.image))


                            val imageUri = Uri.fromFile(getPhotoFileUri(reminder.image!!,context))
                            if (Build.VERSION.SDK_INT < 28) {
                                bitmap.value = MediaStore.Images
                                    .Media.getBitmap(context.contentResolver, imageUri)
                            } else {
                                val source =
                                    ImageDecoder.createSource(context.contentResolver, imageUri)
                                bitmap.value = ImageDecoder.decodeBitmap(source)
                            }

                            bitmap.value?.let { btm ->
                                Image(
                                    bitmap = btm.asImageBitmap(),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(200.dp)
                                        .padding(20.dp)
                                )
                            }
                        }


                    }
                }

            }
        }
    }
}

fun getCardColorSchemeByDate(date: String): Map<String, Color>? {
    val reminderDate = LocalDate.parse(date)
    println(reminderDate.dayOfWeek.name)
    println(ReminderColorConstants[reminderDate.dayOfWeek.name])
    return ReminderColorConstants[reminderDate.dayOfWeek.name]

}

fun getDateIcon(date: String): String {
    val reminderDate = LocalDate.parse(date)
    println(reminderDate.dayOfWeek.name)
    return reminderDate.dayOfWeek.name.substring(0, 3)

}