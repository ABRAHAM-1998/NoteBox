package com.twentytwo.notebox.Activities

import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.twentytwo.notebox.R
import kotlinx.android.synthetic.main.activity_login.*


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
            Toast.makeText(this, "comming soon", Toast.LENGTH_SHORT).show()
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
                    updateUI(user)
                } else {
                    log_text.text = "LOGIN FAILED! WRONG PASSWORD OR EMAIL"
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        baseContext, "Authentication failed  ${task}.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
            }
    }
    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser: FirebaseUser? = auth.currentUser
        updateUI(currentUser)
    }
    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            if (currentUser.isEmailVerified) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                log_text.text = "PLEASE VERYFY YOUR EMAIL ADDRESS " +
                        "\n A MAIL HAS BEEN SEND TO YOUR EMAIL ADDRESS"
                FirebaseAuth.getInstance().signOut()
                Toast.makeText(this, "PLEASE VERYFY YOUR EMAIL ADDRESS ", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "LOGIN FAILED", Toast.LENGTH_SHORT).show()
        }
    }
}