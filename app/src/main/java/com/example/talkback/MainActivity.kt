package com.example.talkback

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.OnInitListener
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class MainActivity : AppCompatActivity(), OnInitListener {
    private lateinit var textToSpeech: TextToSpeech
    private lateinit var editText: EditText
    private lateinit var speakButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText = findViewById(R.id.editText)
        speakButton = findViewById(R.id.speakButton)

        // Initialize TextToSpeech engine
        textToSpeech = TextToSpeech(this, this)

        speakButton.setOnClickListener {
            val textToRead = editText.text.toString().trim()
            if (textToRead.isNotEmpty()) {
                textToSpeech.speak(textToRead, TextToSpeech.QUEUE_FLUSH, null, null)
            }
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = textToSpeech.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                // Handle language data missing or not supported
            } else {
                // TextToSpeech is initialized successfully
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Release TextToSpeech engine when the app is destroyed
        textToSpeech.stop()
        textToSpeech.shutdown()
    }
}
