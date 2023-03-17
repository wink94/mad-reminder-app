package com.windula.reminderapp.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Settings.Global.getString
import android.util.Log
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofenceStatusCodes
import com.google.android.gms.location.GeofencingEvent
import com.windula.reminderapp.R

class GeofenceBroadcastReceiver : BroadcastReceiver() {
    // ...
    override fun onReceive(context: Context?, intent: Intent?) {
        val geofencingEvent = GeofencingEvent.fromIntent(intent)
        if (geofencingEvent.hasError()) {
            val errorMessage = GeofenceStatusCodes
                .getStatusCodeString(geofencingEvent.errorCode)
            Log.e(APP_TAG, errorMessage)
            return
        }

        if (intent != null) {
            if (intent.action == "ACTION_EVENT") {
                val geofenceEvent = GeofencingEvent.fromIntent(intent)
                geofenceEvent?.let {
                    if (geofenceEvent.hasError()) {
                        Log.e("error", geofenceEvent.errorCode.toString())
                        return
                    }
                }

               print("geo ACTION_EVENT")
            }
        }

        // Get the transition type.
        val geofenceTransition = geofencingEvent.geofenceTransition

        // Test that the reported transition was of interest.
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER || geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {

            // Get the geofences that were triggered. A single event can trigger
            // multiple geofences.
            val triggeringGeofences = geofencingEvent.triggeringGeofences

            print(triggeringGeofences.get(0).requestId)
            print("trans"+geofenceTransition)
            // Get the transition details as a String.
//            val geofenceTransitionDetails = getGeofenceTransitionDetails(
//                this,
//                geofenceTransition,
//                triggeringGeofences
//            )

            // Send notification and log the transition details.
//            sendNotification(geofenceTransitionDetails)
//            Log.i(APP_TAG, geofenceTransitionDetails)
        } else {
            // Log the error.
            Log.e(APP_TAG, "error")
        }
    }
}