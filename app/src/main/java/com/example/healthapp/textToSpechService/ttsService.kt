package com.example.healthapp.textToSpechService

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeechService
import android.util.Log
import java.util.Locale

class ttsService: Service(), TextToSpeech.OnInitListener  {
    private lateinit var tts: TextToSpeech
    lateinit var text:String
//    lateinit var text1: String?
    lateinit var final:String

    override fun onCreate() {
        super.onCreate()
        tts = TextToSpeech(this, this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        var text:String = intent?.getStringExtra("med") ?: ""
        var text2: String? = intent?.getStringExtra("desc") ?:""
        var toSpeak: String = "Time for "+text+ " Listen to directions " + text2
        final = toSpeak
//        if (tts.isSpeaking){
//            tts.stop()
//        }
//       tts.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null, null)

        return super.onStartCommand(intent, flags, startId)
    }
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS){
            val result = tts.setLanguage(Locale.US)
            if (result==TextToSpeech.LANG_MISSING_DATA || result==TextToSpeech.LANG_NOT_SUPPORTED){
                Log.d("TTS", "Language is not supported")
            }
            else{
                if (tts.isSpeaking){
            tts.stop()
        }
       tts.speak(final, TextToSpeech.QUEUE_FLUSH, null, null)
            }
        }
        else{
            Log.d("TTS", "TTS initialization failed")
        }
    }

    override fun onDestroy() {
        if (tts!=null){
            tts.stop()
            tts.shutdown()
        }
        super.onDestroy()
    }
}