package com.example.talkback

import android.annotation.SuppressLint
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.OnInitListener
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import java.util.Locale

class MainActivity : AppCompatActivity(), OnInitListener {
    private lateinit var textToSpeech: TextToSpeech
    private lateinit var editText: EditText
    private lateinit var speakButton: Button
    private lateinit var editName: EditText
    private lateinit var textInputLayout1: TextInputLayout

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText = findViewById(R.id.editText)
        speakButton = findViewById(R.id.speakButton)

        // Initialize TextToSpeech engine
        textToSpeech = TextToSpeech(this) { status ->
            if (status != TextToSpeech.ERROR) {
                textToSpeech.language = Locale.getDefault()
            } else {
                Toast.makeText(this, "Text-to-speech initialization failed.", Toast.LENGTH_SHORT).show()
            }
        }

        speakButton.setOnClickListener {
            val textToRead = editText.text.toString().trim()
            if (textToRead.isNotEmpty()) {
                textToSpeech.speak(textToRead, TextToSpeech.QUEUE_FLUSH, null, null)
            }
        }

        editName = findViewById(R.id.editName)
        textInputLayout1 = findViewById(R.id.textInputLayout1)
        textInputLayout1.setEndIconOnClickListener {
            val inputText = editName.text.toString()
            if (inputText.isNotEmpty()) {
                readAloud(inputText)
            }
        }

    }
    private fun readAloud(text: String) {
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
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
