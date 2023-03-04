package com.windula.reminderapp

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

object Graph {
    lateinit var appContext: Context

    fun provide(context: Context) {
        appContext = context
    }
}

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)
    }
}

