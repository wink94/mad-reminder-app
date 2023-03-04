package com.windula.reminderapp.util

import android.util.Log
import androidx.compose.ui.graphics.Color
import com.windula.reminderapp.ui.theme.*
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.TimeUnit

object Constants {

    val ReminderColorConstants = mapOf<String,Map<String,Color>>(
        "SUNDAY" to mapOf<String,Color>("backGround"  to purple_100,"text" to white,"dayCircleBackgroundColor" to blue_100),
        "MONDAY" to mapOf<String,Color>("backGround"  to green_100,"text" to white,"dayCircleBackgroundColor" to Purple200),
        "TUESDAY" to mapOf<String,Color>("backGround"  to blue_200,"text" to white,"dayCircleBackgroundColor" to orange_200),
        "WEDNESDAY" to mapOf<String,Color>("backGround"  to green_300,"text" to Color.Black,"dayCircleBackgroundColor" to red_200),
        "THURSDAY" to mapOf<String,Color>("backGround"  to blue_100,"text" to white,"dayCircleBackgroundColor" to red_200),
        "FRIDAY" to mapOf<String,Color>("backGround"  to orange_200,"text" to Color.Black,"dayCircleBackgroundColor" to green_100),
        "SATURDAY" to mapOf<String,Color>("backGround"  to green_300,"text" to Color.Black,"dayCircleBackgroundColor" to Purple200)
    )
}
val APP_TAG = "ReminderApp"

val CHANNEL_ID = "CHANNEL_ID"
fun getTimestamp():String{
    return DateTimeFormatter
        .ofPattern("yyyy_MM_dd_HH_mm_ss_SSSSSS")
        .withZone(ZoneOffset.UTC)
        .format(Instant.now())
}


fun getTimeDelay(unit:TimeUnit,date:String,time:String): Long {
    var diff:Long = 0
    if (unit == TimeUnit.MINUTES){
        val date = SimpleDateFormat("yyyy-MM-dd HH:mm").parse("$date $time")
        diff = (date.time - Date().time)/1000/60
        Log.i(APP_TAG, "time dealy (min) ${diff.toString()}")

    }

    return diff
}