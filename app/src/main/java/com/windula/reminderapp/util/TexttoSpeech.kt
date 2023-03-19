package com.windula.reminderapp.util

import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import com.windula.core_domain.entity.Reminder
import java.util.*

class TexttoSpeech(private val reminder: Reminder,context: Context) : TextToSpeech.OnInitListener {
    private var tts: TextToSpeech? = TextToSpeech(context,this)
    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts!!.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS","The Language not supported!")
            } else {
                val reminderText:CharSequence = "Reminder title is ${reminder.title} and reminder message is ${reminder.message} , reminder date is ${reminder.reminderDate} finally reminder rime is ${reminder.reminderTime}"
                tts!!.speak(reminderText,TextToSpeech.QUEUE_FLUSH, null,"")
            }
        }
    }
}