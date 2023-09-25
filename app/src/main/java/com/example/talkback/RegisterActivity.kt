package com.example.talkback
import android.content.Intent
import android.widget.EditText
import android.widget.TextView
import com.example.talkback.R
import android.os.Bundle
import android.telephony.PhoneNumberUtils
import androidx.annotation.NonNull
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.OnInitListener
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import java.util.*
class RegisterActivity : AppCompatActivity(), OnInitListener {

    private lateinit var textToSpeech: TextToSpeech
    private lateinit var editName: EditText
    private lateinit var editContact: TextInputEditText
    private lateinit var editTextOTP: TextView
    private lateinit var buttonSendOTP: Button
    private lateinit var buttonVerifyOTP: Button
    private var verificationId: String? = null
    private lateinit var mAuth: FirebaseAuth
    private lateinit var textviewclickable: TextView
    private lateinit var textInputLayout1: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        editContact = findViewById(R.id.editContact)
        editTextOTP = findViewById(R.id.editTextOTP)
        buttonSendOTP = findViewById(R.id.buttonSendOTP)
        buttonVerifyOTP = findViewById(R.id.buttonVerifyOTP)

        mAuth = FirebaseAuth.getInstance()

        buttonSendOTP.setOnClickListener {
            val phoneNumber = editContact.text.toString().trim()
            sendOTP(phoneNumber)
        }

        buttonVerifyOTP.setOnClickListener {
            val otp = editTextOTP.text.toString().trim()
            verifyOTP(otp)
        }


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
                Toast.makeText(this, "Text-to-speech initialization failed.", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }


    private fun sendOTP(phoneNumber: String) {
//        val formattedPhoneNumber = "+91$phoneNumber"

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            "+91"+ editContact.text.toString(),
            60,
            TimeUnit.SECONDS,
            this,
            object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                    // Automatically handle the verification if the SMS code is detected
                    signInWithPhoneAuthCredential(phoneAuthCredential)
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    Toast.makeText(this@RegisterActivity, "Verification Failed: ${e.message}", Toast.LENGTH_LONG).show()
                    e.printStackTrace()
                }


                override fun onCodeSent(s: String, forceResendingToken: PhoneAuthProvider.ForceResendingToken) {
                    super.onCodeSent(s, forceResendingToken)
                    verificationId = s
                    buttonSendOTP.visibility = View.GONE
                    buttonVerifyOTP.visibility = View.VISIBLE
                    editContact.isEnabled = false
                    editTextOTP.visibility = View.VISIBLE
                }
            })
    }

    private fun verifyOTP(otp: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId ?: "", otp)
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Authentication successful
                    // You can add further actions like saving user data to Firebase Realtime Database
                    Toast.makeText(this, "Authentication successful!", Toast.LENGTH_SHORT).show()
                } else {
                    // Authentication failed
                    Toast.makeText(this, "Authentication failed!", Toast.LENGTH_SHORT).show()
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