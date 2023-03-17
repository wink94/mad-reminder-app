package com.windula.reminderapp.util

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.android.gms.location.*
import com.windula.core_domain.entity.Reminder


class GeofencingService(val context: Context){

    private var geofencingClient: GeofencingClient = LocationServices.getGeofencingClient(context)


//    fun getGeofencingRequest(): GeofencingRequest {
//        return GeofencingRequest.Builder().apply {
//            setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
//            addGeofences(geofenceList)
//        }.build()
//    }

    private val geofenceIntent: PendingIntent by lazy {
        val intent = Intent(context, GeofenceBroadcastReceiver::class.java)
        intent.action = "ACTION_EVENT"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getBroadcast(
                context, 0, intent,
                PendingIntent.FLAG_MUTABLE
            )
        } else {
            PendingIntent.getBroadcast(
                context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }
    }




    fun startGeoFence(dataItem: Reminder) {
        val geoFence = Geofence.Builder()
            .setRequestId(dataItem.title)
            .setCircularRegion(
                dataItem.locationX!!,
                dataItem.locationY!!,
                150f
            )
            .setExpirationDuration(Geofence.NEVER_EXPIRE)
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
            .build()

        val geofenceRequest = GeofencingRequest.Builder()
            .setInitialTrigger(Geofence.GEOFENCE_TRANSITION_ENTER)
            .addGeofence(geoFence)
            .build()

        geofenceRequest.geofences
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
        geofencingClient.addGeofences(geofenceRequest, geofenceIntent)?.run {
            addOnSuccessListener {

                Toast.makeText(
                    context,
                    "Geofence Added",
                    Toast.LENGTH_SHORT
                ).show()
            }
            addOnFailureListener {
                print(it)
                Log.e(APP_TAG,it.toString())
                Toast.makeText(
                    context,
                    "Geofence Failed to Add",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


    }

    fun checkLocationSettingsAndStartGeofence(dataItem: Reminder) {
        val locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_LOW_POWER
        }
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
        val client = LocationServices.getSettingsClient(context)
        val task = client.checkLocationSettings(builder.build())
        task.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                startGeoFence(dataItem)
            } else {
//                Snackbar.make(
//                    requireView(),
//                    "Location is not enabled on the device",
//                    Snackbar.LENGTH_INDEFINITE
//                ).setAction(android.R.string.ok) {
//                    checkLocationSettingsAndStartGeofence(dataItem)
//                }.show()

            }
        }

    }
}