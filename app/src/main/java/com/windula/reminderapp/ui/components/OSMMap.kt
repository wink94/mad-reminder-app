//package com.windula.reminderapp.ui.components
//
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.ui.Modifier
//import ovh.plrapps.mapcompose.api.addLayer
//import ovh.plrapps.mapcompose.api.enableRotation
//import ovh.plrapps.mapcompose.core.TileStreamProvider
//import ovh.plrapps.mapcompose.ui.MapUI
//import ovh.plrapps.mapcompose.ui.state.MapState
//import java.io.File
//import java.io.FileInputStream
//
///* Inside your view-model */
//val tileStreamProvider = TileStreamProvider { row, col, zoomLvl ->
//    FileInputStream(File("path/{$zoomLvl}/{$row}/{$col}.jpg")) // or it can be a remote HTTP fetch
//}
//
//val state: MapState by mutableStateOf(
//    MapState(4, 4096, 4096).apply {
//        addLayer(tileStreamProvider)
//        enableRotation()
//    }
//)
//
///* Inside a composable */
//@Composable
//fun MapContainer(
//    modifier: Modifier = Modifier
//) {
//    MapUI(modifier, state = state)
//}