package com.windula.reminderapp.ui.home

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.windula.core_domain.entity.Reminder
import com.windula.reminderapp.util.Constants.ReminderColorConstants
import java.time.LocalDate


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ReminderCard(
    reminder:Reminder

    ) {
    println(reminder)
    var expand by remember { mutableStateOf(false) } // Expand State
    val rotationState by animateFloatAsState(if (expand) 180f else 0f) // Rotation State
    var stroke by remember { mutableStateOf(1) } // Stroke State

    val getColorMap = getCardColorSchemeByDate(reminder.reminderDate)

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
                        .padding(15.dp)
                        .drawBehind {
                            drawCircle(
                                color = getColorMap.get("dayCircleBackgroundColor")!!,
                                radius = this.size.maxDimension
                            )
                        },
                    text = "S",
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp,
                )


                Text(
                    text = reminder.title,
                    color = getColorMap.get("text")!!, // Header Color
                    fontSize = 20.sp,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier
                        .weight(.9f)
                        .padding(start = 20.dp)
                )
                IconButton(
                    modifier = Modifier
                        .rotate(rotationState)
                        .weight(.1f),
                    onClick = {
//                        expand = !expand
//                        stroke = if (expand) 2 else 1
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        tint = Color.White,
                        contentDescription = "Edit reminder"
                    )
                }
                Text(
                    text = reminder.title,
                    color = getColorMap.get("text")!!, // Header Color
                    fontSize = 20.sp,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier
                        .weight(.9f)
                        .padding(start = 20.dp)
                )
                IconButton(
                    modifier = Modifier
                        .rotate(rotationState)
                        .weight(.1f),
                    onClick = {
//                        expand = !expand
//                        stroke = if (expand) 2 else 1
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
                        .weight(.1f),
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
                Text(
                    text = reminder.reminderDate,
                    color = getColorMap.get("text")!!, // date Color
                    fontSize = 16.sp,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                )
                Text(
                    text = reminder.message,
                    color = getColorMap.get("text")!!, // Description Color
                    fontSize = 16.sp,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                )
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