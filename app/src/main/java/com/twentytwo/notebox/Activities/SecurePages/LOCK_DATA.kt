package com.twentytwo.notebox.Activities.SecurePages

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.twentytwo.notebox.Activities.CERTIFICATES.CertificatesActivity
import com.twentytwo.notebox.Activities.IDCARDS.Idcards_Home
import com.twentytwo.notebox.R
import kotlinx.android.synthetic.main.activity_lock_data.*
import java.util.concurrent.Executor

class LOCK_DATA : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lock_data)
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence
                ) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(
                        applicationContext,
                        "Authentication error: $errString", Toast.LENGTH_SHORT
                    )
                        .show()
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(
                        applicationContext,
                        "Authentication succeeded!", Toast.LENGTH_SHORT
                    )
                        .show()
                    if (intent.getStringExtra("name") == "certificates") {
                        val intent = Intent(this@LOCK_DATA, CertificatesActivity::class.java)
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        this@LOCK_DATA.startActivity(intent)

                    } else if (intent.getStringExtra("name") == "idcards") {
                        val intent = Intent(this@LOCK_DATA, Idcards_Home::class.java)
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        this@LOCK_DATA.startActivity(intent)
                    }
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(
                        applicationContext, "Authentication failed",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login | Note~Box")
            .setSubtitle("Log in using your biometric credential")
            .setNegativeButtonText("Dismiss")
            .build()


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        var prefData: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val lock = prefData.getBoolean("numpass", false)
        val fppass = prefData.getBoolean("fpauth", false)
        if (fppass && lock) {
            biometricPrompt.authenticate(promptInfo)
            fingerp.setOnClickListener {
                biometricPrompt.authenticate(promptInfo)

            }
        }else{
            fingerp.visibility = View.INVISIBLE
        }

        auth = FirebaseAuth.getInstance()
        login_btn2.setOnClickListener {
            do_login()
        }
    }

    private fun do_login() {

        if (email_bx2.text.toString().isEmpty()) {
            email_bx2.error = "please enter your email"
            email_bx2.requestFocus()
            return
        } else if (email_bx2.text.toString().length <= 4) {
            email_bx2.error = "please enter a valid email"
            email_bx2.requestFocus()
            return
        }
        if (pass_bx2.text.toString().isEmpty()) {
            pass_bx2.error = "please enter password"
            pass_bx2.requestFocus()
            return
        } else if (pass_bx2.text.toString().length <= 5) {
            pass_bx2.error = "please check the password"
            pass_bx2.requestFocus()
            return
        }
        auth.signInWithEmailAndPassword(email_bx2.text.toString(), pass_bx2.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    savepass(user, pass_bx2.text.toString())
                } else {
                    log_text2.text = "LOGIN FAILED! WRONG PASSWORD OR EMAIL"
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        baseContext, "Authentication failed  ${task}.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun savepass(currentUser: FirebaseUser?, codepas: String) {
        val uid = currentUser?.uid
        val db = Firebase.firestore
        val docRef = db.collection("UsersDetails").document("$uid")
        if (currentUser != null) {
            docRef
                .update("password", "$codepas")
                .addOnSuccessListener {
                }
                .addOnFailureListener { e ->
                    Log.w("TAG", "Error updating document", e)
                }
            if (intent.getStringExtra("name") == "certificates") {
                val intent = Intent(this, CertificatesActivity::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP);
                this.startActivity(intent)

            } else if (intent.getStringExtra("name") == "idcards") {
                val intent = Intent(this, Idcards_Home::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP);
                this.startActivity(intent)
            }
        }
    }
}