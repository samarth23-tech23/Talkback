package com.example.talkback

import android.content.Intent
import android.os.Bundle

import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.OnInitListener
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.talkback.R
import java.util.Locale

class RegisterActivity : AppCompatActivity(), OnInitListener {
    private lateinit var editName: EditText
    private lateinit var editUDID: EditText
    private lateinit var editContact: EditText
    private lateinit var editCaretakerContact: EditText
    private lateinit var displayButton: Button
    private lateinit var resultTextView: TextView
    private lateinit var textviewclickable: TextView
    private lateinit var textToSpeech: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        editName = findViewById(R.id.editName)
        editUDID = findViewById(R.id.editUDID)
        editContact = findViewById(R.id.editContact)
        editCaretakerContact = findViewById(R.id.editCaretakerContact)
        displayButton = findViewById(R.id.displayButton)
        resultTextView = findViewById(R.id.resultTextView)

        textviewclickable = findViewById(R.id.textview_clickable)

        textviewclickable.setOnClickListener(View.OnClickListener {
            // Handle the click event here
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        })
        // Initialize TextToSpeech engine
        textToSpeech = TextToSpeech(this, this)

        displayButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                val name = editName.text.toString()
                val udid = editUDID.text.toString()
                val contact = editContact.text.toString()
                val caretakerContact = editCaretakerContact.text.toString()

                val resultText = """
                    Name: $name
                    UDID: $udid
                    Contact: $contact
                    Caretaker's Contact: $caretakerContact
                """.trimIndent()

                // Display entered values
                resultTextView.text = resultText

                // Speak the entered values
                textToSpeech.speak(resultText, TextToSpeech.QUEUE_FLUSH, null, null)
            }
        })

    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            // Set the language for TextToSpeech (e.g., US English)
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
