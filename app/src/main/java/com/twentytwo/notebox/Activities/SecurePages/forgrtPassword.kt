package com.twentytwo.notebox.Activities.SecurePages

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.twentytwo.notebox.R
import kotlinx.android.synthetic.main.activity_forgrt_password.*

class forgrtPassword : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgrt_password)

        auth = FirebaseAuth.getInstance()



        resetbutton.setOnClickListener {
            if(resetemail.text.length < 6){
                resetemail.error = "please enter a valid email"
                resetemail.requestFocus()
                return@setOnClickListener
            }
            auth.sendPasswordResetEmail(resetemail.text.toString())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        resetemail.setTextColor(Color.parseColor("#FF07F62F"))
                        Log.d("TAG", "Email sent.")
                    resetmessage.text = "A password reset email has been sent \n " +
                            "check your email ,then follow the link to reset." +
                            "\n Email will valid only for 15 minutes"
                        resetemail.text.clear()

                    } else {
                        Log.d("TAG", "Email sent failsesd.")
                        resetmessage.text = "Failed to sent Email \n plesase try again later.."
                    }
                }
        }
    }
}