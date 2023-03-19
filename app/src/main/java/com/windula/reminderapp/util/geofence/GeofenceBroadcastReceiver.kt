package com.windula.reminderapp.util.geofence

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent
import com.google.android.gms.location.LocationServices
import com.windula.reminderapp.R
import com.windula.reminderapp.util.GEO_FENCE_CHANNEL_ID
import kotlin.random.Random

class GeofenceBroadcastReceiver :  BroadcastReceiver() {
    lateinit var key: String
    lateinit var text: String

    override fun onReceive(context: Context?, intent: Intent?) {
        println("geo onReceive")
        if (context != null) {
            val geofencingEvent = GeofencingEvent.fromIntent(intent)
            val geofencingTransition = geofencingEvent.geofenceTransition
            println("geo onReceive not null")
            if (geofencingTransition == Geofence.GEOFENCE_TRANSITION_ENTER || geofencingTransition == Geofence.GEOFENCE_TRANSITION_DWELL) {
                // Retrieve data from intent
                if (intent != null) {
                    key = intent.getStringExtra("key")!!
                    text = intent.getStringExtra("message")!!
                }
                println("geo fence enterd")

                showNotification(context,"$key $text")

                // remove geofence
                val triggeringGeofences = geofencingEvent.triggeringGeofences

                removeGeofences(context, triggeringGeofences)
            }
        }
    }

    fun showNotification(context: Context?, message: String) {
        var notificationId = 1589
        notificationId += Random(notificationId).nextInt(1, 30)

        val notificationBuilder = NotificationCompat.Builder(context!!.applicationContext, GEO_FENCE_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_rounded_notification)
            .setContentTitle(context.getString(R.string.app_name))
            .setContentText(message)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(message)
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                GEO_FENCE_CHANNEL_ID,
                context.getString(R.string.app_name),
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = context.getString(R.string.app_name)
            }
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(notificationId, notificationBuilder.build())
    }

    fun removeGeofences(context: Context, triggeringGeofenceList: MutableList<Geofence>) {
        val geofenceIdList = mutableListOf<String>()
        for (entry in triggeringGeofenceList) {
            geofenceIdList.add(entry.requestId)
        }
        LocationServices.getGeofencingClient(context).removeGeofences(geofenceIdList)
    }
}