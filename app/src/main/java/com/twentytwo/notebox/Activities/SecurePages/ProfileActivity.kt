package com.twentytwo.notebox.Activities.SecurePages

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.twentytwo.notebox.R
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val uid = FirebaseAuth.getInstance().currentUser.uid
        val db = Firebase.firestore


        val docRef = db.collection("UsersDetails").document("$uid")

        val source = Source.CACHE

        docRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Document found in the offline cache
                val document = task.result
                Toast.makeText(this, "${document?.data}", Toast.LENGTH_SHORT).show()
                Log.d("TAG", "Cached document data: ${document?.data}")
                if (document != null) {
                    val users = document.toObject<User>()
                        profile_name.text = users?.name.toString()
                    profile_email.text = users?.email.toString()
                    profile_mobile.text = users?.mobile.toString()
                    profile_gender.text = "Male :: ${users?.gender.toString()}"


                }
            }


        }
    }
}