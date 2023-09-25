package com.example.talkback

import android.content.Intent
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.OnInitListener
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.talkback.databinding.ActivityLoginBinding
import java.util.Locale

class LoginActivity : AppCompatActivity(), OnInitListener {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var textToSpeech: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val rootView = binding.root
        setContentView(rootView)

        // Initialize TextToSpeech engine
        textToSpeech = TextToSpeech(this, this)

        binding.buttonLogin.setOnClickListener {
            // Start the main screen activity
            val intent = Intent(this, MainScreen::class.java)
            startActivity(intent)

            // Speak a welcome message
            val welcomeMessage = "Welcome to the app."
            textToSpeech.speak(welcomeMessage, TextToSpeech.QUEUE_FLUSH, null, null)
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
        // Release TextToSpeech engine when the activity is destroyed
        textToSpeech.stop()
        textToSpeech.shutdown()
    }
}
