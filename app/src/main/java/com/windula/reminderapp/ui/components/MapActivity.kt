//package com.windula.reminderapp.ui.components
//
//import android.os.Bundle
//import androidx.appcompat.app.AppCompatActivity
//import com.windula.reminderapp.databinding.ActivityMapBinding
//import org.osmdroid.config.Configuration
//import org.osmdroid.util.GeoPoint
//import org.osmdroid.views.MapView
//
//class MapActivity : AppCompatActivity() {
//    lateinit var mMap : MapView
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        val binding = ActivityMapBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
////        mMap = binding.
////        mMap.setTileSource(TileSourceFactory.MAPNIK)
//
//
//
//        Configuration.getInstance().load(applicationContext, getSharedPreferences("phonebook_app", MODE_PRIVATE))
//
//        val longitude = intent.getDoubleExtra("longitude", 36.7783)
//        val latitude = intent.getDoubleExtra("latitude", 119.4179)
//
//
//        val controller = mMap.controller
//
//        val mapPoint = GeoPoint(latitude, longitude)
//
//        controller.setZoom(9.5)
//
//        controller.animateTo(mapPoint)
//
//
//    }
//}