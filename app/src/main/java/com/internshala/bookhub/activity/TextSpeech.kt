package com.internshala.bookhub.activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import com.internshala.bookhub.R
import kotlinx.android.synthetic.main.activity_text_speech.*
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.util.*

class TextSpeech : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var tts: TextToSpeech? = null
    private var btnSpeak: ImageButton? = null
    private var etSpeak: TextView? = null
    lateinit var toolbars: Toolbar

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_speech)

        //read_json()

        // view binding button and edit text
        btnSpeak = findViewById(R.id.btn_speak)
        etSpeak = findViewById(R.id.et_input)


        toolbars = findViewById(R.id.toolbars)
        setSupportActionBar(toolbars)
        supportActionBar?.title = "Novel"

        btnSpeak!!.isEnabled = false

        // TextToSpeech(Context: this, OnInitListener: this)
        tts = TextToSpeech(this, this)

        btnSpeak!!.setOnClickListener { speakOut() }

    }

    /*fun read_json() {
        var jsons: String? = null
        //var bookId: String? = "100"

        val inputStreams: InputStream = assets.open("First.json")
        jsons = inputStreams.bufferedReader().use { it.readText() }
        //val jsonObj = JSONObject(jsons)
        et_input.text = jsons
    }*/



    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts!!.setLanguage(Locale.ENGLISH)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The Language not supported!")
            } else {
                btnSpeak!!.isEnabled = true
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun speakOut() {
        val text = etSpeak!!.text.toString()
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }


    public override fun onDestroy() {
        // Shutdown TTS when
        // activity is destroyed
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }
}

