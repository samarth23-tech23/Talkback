package com.example.talkback

import android.content.Intent
import android.widget.EditText
import android.widget.TextView
import com.example.talkback.R
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.OnInitListener
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import java.util.*

class RegisterActivity : AppCompatActivity(), OnInitListener {

    private lateinit var textToSpeech: TextToSpeech
    private lateinit var editName: EditText
    private lateinit var textviewclickable: TextView
    private lateinit var textInputLayout1: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Initialize TextToSpeech
        textToSpeech = TextToSpeech(this, this)

        textviewclickable = findViewById(R.id.textview_clickable)

        textviewclickable.setOnClickListener(View.OnClickListener {
            // Handle the click event here
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        })

        editName = findViewById(R.id.editName)
        textInputLayout1 = findViewById(R.id.textInputLayout1)
        textInputLayout1.setEndIconOnClickListener {
            val inputText = editName.text.toString()
            if (inputText.isNotEmpty()) {
                readAloud(inputText)
            }
        }
        textToSpeech = TextToSpeech(this) { status ->
            if (status != TextToSpeech.ERROR) {
                textToSpeech.language = Locale.getDefault()
            } else {
                Toast.makeText(this, "Text-to-speech initialization failed.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun readAloud(text: String) {
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = textToSpeech.setLanguage(Locale.US) // Set the desired language
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "Language not supported.", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "TextToSpeech initialization failed.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        if (textToSpeech.isSpeaking) {
            textToSpeech.stop()
        }
        textToSpeech.shutdown()
        super.onDestroy()
    }
}
