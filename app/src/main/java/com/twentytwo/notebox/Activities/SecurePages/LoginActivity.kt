package com.twentytwo.notebox.Activities.SecurePages

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.twentytwo.notebox.Activities.DashBoard.MainActivity
import com.twentytwo.notebox.R
import kotlinx.android.synthetic.main.activity_login.*
import java.text.SimpleDateFormat
import java.util.*


class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()

        //=====================FULL-SCREEN===========================
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
            )
        }
        //--------------------------------------------------------------------
        //================INTENT TO SIGNUP============
        signup_btn.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
        login_btn.setOnClickListener {
            do_login()
        }
        btn_forgetPass.setOnClickListener {
            startActivity(Intent(this, forgrtPassword::class.java))
        }
    }

    private fun do_login() {

        if (email_bx.text.toString().isEmpty()) {
            email_bx.error = "please enter your email"
            email_bx.requestFocus()
            return
        } else if (email_bx.text.toString().length <= 4) {
            email_bx.error = "please enter a valid email"
            email_bx.requestFocus()
            return
        }
        if (pass_bx.text.toString().isEmpty()) {
            pass_bx.error = "please enter password"
            pass_bx.requestFocus()
            return
        } else if (pass_bx.text.toString().length <= 5) {
            pass_bx.error = "please check the password"
            pass_bx.requestFocus()
            return
        }
        auth.signInWithEmailAndPassword(email_bx.text.toString(), pass_bx.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                        val pascode = pass_bx.text.toString()
                    updateUI(user,pascode)

                } else {
                    log_text.text = "LOGIN FAILED! WRONG PASSWORD OR EMAIL"
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        baseContext, "Authentication failed  ${task}.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null, pass_bx.text.toString())
                }
            }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser: FirebaseUser? = auth.currentUser
        updateUI(currentUser, pass_bx.text.toString())
    }

    private fun updateUI(currentUser: FirebaseUser?, passcode: String) {
        val uid = currentUser?.uid
        val db = Firebase.firestore
        val docRef = db.collection("UsersDetails").document("$uid")
        val sdf = SimpleDateFormat("dd/M/yyy hh:mm:ss")
        val currentday = sdf.format(Date())

        if (currentUser != null) {
            if (currentUser.isEmailVerified) {
                docRef
                    .update("lastlogin", "$currentday")
                    .addOnSuccessListener {
                        Log.d("lastlogin", "lastloginsuccess")
                    }

                    .addOnFailureListener { e ->
                        Log.w(
                            "TAG",
                            "Error updating document",
                            e
                        )
                    }

                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                log_text.text = "PLEASE VERYFY YOUR EMAIL ADDRESS " +
                        "\n A MAIL HAS BEEN SEND TO YOUR EMAIL ADDRESS"
                FirebaseAuth.getInstance().signOut()
                Toast.makeText(this, "PLEASE VERYFY YOUR EMAIL ADDRESS ", Toast.LENGTH_SHORT).show()
            }
            if(passcode.length > 3){
                docRef
                    .update("password", "$passcode")
                    .addOnSuccessListener {
                        Log.d("lastlogin", "lastloginsuccess")
                    }

                    .addOnFailureListener { e ->
                        Log.w(
                            "TAG",
                            "Error updating document",
                            e
                        )
                    }
            }
        } else {
            Toast.makeText(this, "LOGIN FAILED", Toast.LENGTH_SHORT).show()
        }
    }
}