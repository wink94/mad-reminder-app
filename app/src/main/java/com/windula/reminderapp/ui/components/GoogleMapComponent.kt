package com.windula.reminderapp.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.ktx.awaitMap
import com.windula.reminderapp.ui.reminder.ReminderViewModel
import com.windula.reminderapp.util.rememberMapViewWithLifecycle
import kotlinx.coroutines.launch
import java.util.*

@Composable
fun GoogleMapComponent(navController: NavController,viewModel: ReminderViewModel = hiltViewModel()) {
    val mapView: MapView = rememberMapViewWithLifecycle()
    val coroutineScope = rememberCoroutineScope()

    AndroidView({mapView}) {
        coroutineScope.launch {
            val map = mapView.awaitMap()
            map.uiSettings.isZoomControlsEnabled = true
            map.uiSettings.isScrollGesturesEnabled = true
            val location = LatLng(48.859343671137204,2.29278679)
            map.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(location.latitude, location.longitude),
                    10f
                )
            )
            setMapLongClick(map, navController)
        }
    }
}

private fun setMapLongClick(
    map: GoogleMap,
    navController: NavController
) {
    map.setOnMapLongClickListener { latlng ->
        val snippet = String.format(
            Locale.getDefault(),
            "Lat: %1$.2f, Lng: %2$.2f",
            latlng.latitude,
            latlng.longitude
        )

        map.addMarker(
            MarkerOptions().position(latlng).title("Reminder location").snippet(snippet)
        ).apply {
            navController.previousBackStackEntry
                ?.savedStateHandle
                ?.set("location_data", latlng)
        }
    }
}
