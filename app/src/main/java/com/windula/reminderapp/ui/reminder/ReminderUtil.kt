package com.windula.reminderapp.ui.reminder

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import com.windula.reminderapp.util.APP_TAG


fun startSpeechToText(vm: ReminderViewModel, ctx: Context, finished: ()-> Unit,text:String?=null) {
    val speechRecognizer = SpeechRecognizer.createSpeechRecognizer(ctx)

    if (!SpeechRecognizer.isRecognitionAvailable(ctx)) {
        Log.e(APP_TAG,"Speech not Available")
        // if the intent is not present we are simply displaying a toast message.
        Toast.makeText(ctx, "Speech not Available", Toast.LENGTH_SHORT).show()
    }

    val speechRecognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
    speechRecognizerIntent.putExtra(
        RecognizerIntent.EXTRA_LANGUAGE_MODEL,
        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM,
    )
    println("startSpeechToText")
    // Optionally I have added my mother language
    speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "el_GR")

    speechRecognizer.setRecognitionListener(object : RecognitionListener {
        override fun onReadyForSpeech(bundle: Bundle?) {
            Log.i(APP_TAG,"onReadyForSpeech")
        }
        override fun onBeginningOfSpeech() {
            Log.i(APP_TAG,"onBeginningOfSpeech")
        }
        override fun onRmsChanged(v: Float) {}
        override fun onBufferReceived(bytes: ByteArray?) {}
        override fun onEndOfSpeech() {
            finished()
            // changing the color of your mic icon to
            // gray to indicate it is not listening or do something you want
            println("startSpeechToText finish")
        }

        override fun onError(i: Int) {
            Log.e(APP_TAG,"startSpeechToText error "+i.toString())

//            when(i){
//                SpeechRecognizer.ERROR_AUDIO
//            }
        }

        override fun onResults(bundle: Bundle) {
            val result = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            Log.e(APP_TAG,"onResults "+result.toString())
            if (result != null) {
                // attaching the output
                // to our viewmodel
                vm.textFromSpeech = result[0]
                println(result[0])
                println(result)
            }
        }

        override fun onPartialResults(bundle: Bundle) {}
        override fun onEvent(i: Int, bundle: Bundle?) {}

    })
    speechRecognizer.startListening(speechRecognizerIntent)
}