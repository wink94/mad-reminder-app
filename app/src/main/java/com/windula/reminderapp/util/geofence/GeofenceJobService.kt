package com.windula.reminderapp.util.geofence

import android.annotation.SuppressLint
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.util.Log
import com.windula.reminderapp.util.APP_TAG

@SuppressLint("SpecifyJobSchedulerIdRange")
class GeofenceJobService: JobService() {
    var jobCancelled = false

    override fun onStartJob(params: JobParameters?): Boolean {
        Log.d(APP_TAG, "Job started")
        doBackgroundWork(params)
        return true
    }

    private fun doBackgroundWork(params: JobParameters?) {
        Thread {
            kotlin.run {
                if (jobCancelled) {
                    return@Thread
                }
                showNotification(applicationContext, "Reminder job service scheduler")
                jobFinished(params, true)
            }
        }.start()
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        Log.d(APP_TAG, "Job cancelled before completion")
        jobCancelled = true
        return true
    }

    fun showNotification(context: Context?, message: String) {
        Log.i(APP_TAG,message)
    }

}