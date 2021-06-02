package com.twentytwo.notebox.Activities.LOCKER

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.twentytwo.notebox.Activities.Notes.Notes
import com.twentytwo.notebox.R
import kotlinx.android.synthetic.main.activity_locker_login_page.*


data class UserList(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val mobile: String = "",
    val password: String = "",
    val date_created: String = "",
    val gender: String = "",
    val termsncd: Boolean = false,
    val lkeystatus: Boolean = false,
    val lkey: String = ""

)

class LockerLoginPage : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_locker_login_page)

        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
            )
        }
        val uid = FirebaseAuth.getInstance().currentUser.uid
        val db = Firebase.firestore

        val docRef = db.collection("UsersDetails").document("$uid")


        docRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Document found in the offline cache
                val document = task.result
                if (document != null) {
                    val users = document.toObject<UserList>()
                    if (users?.lkeystatus!!) {
                        crt_pass2.visibility = View.INVISIBLE
                        pass_text_head.text = "ENTER YOUR UNIQUE PASSWD"
                        button_LOcker.text = "ENTER"

                        button_LOcker.setOnClickListener {
                            if (crt_pass1.text.toString() == users.lkey) {
                                crt_pass1.text.clear()
                                startActivity(Intent(this, LockerHome::class.java))
                            }else{
                                errTxt.text = "WRONG PASSWORD!"
                            }
                        }


                    } else if (!users.lkeystatus) {
                        errTxt.visibility = View.INVISIBLE
                        button_LOcker.setOnClickListener {

                            if (crt_pass1.text.toString().length < 4) {
                                crt_pass1.error = "pass must be 4 digit"
                                return@setOnClickListener
                            }
                            if (crt_pass1.text.toString() != crt_pass2.text.toString()) {
                                crt_pass2.error = "Passwd mismatched"
                                return@setOnClickListener
                            }
                            docRef
                                .update("lkey", "${crt_pass2.text.toString()}", "lkeystatus", true)
                                .addOnSuccessListener {
                                    val intent =
                                        Intent(
                                            this,
                                            LockerLoginPage::class.java
                                        )

                                    intent.flags =
                                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                    startActivity(intent)
                                }

                                .addOnFailureListener { e ->
                                    Log.w(
                                        "TAG",
                                        "Error updating document",
                                        e
                                    )
                                }

                        }
                    }
                }
            }

        }
    }
}