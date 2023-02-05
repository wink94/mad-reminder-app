package com.windula.reminderapp.util

import android.content.Context
import com.windula.reminderapp.R

class SharedPref(context: Context) {

    private val sharedPrefAuthData = context.getSharedPreferences(
        context.getString(R.string.preference_file), Context.MODE_PRIVATE
    )

    init {
        with(sharedPrefAuthData.edit()) {
            putString("username", "admin")
            putString("password", "admin")
            apply()
        }
    }

    fun validateLoginData(username: String, password: String): Boolean {
        val usernameStored = sharedPrefAuthData.getString("username", "")
        val pwdStored = sharedPrefAuthData.getString("password", "")

        return username == usernameStored && password == pwdStored
    }
}