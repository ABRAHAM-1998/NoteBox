package com.twentytwo.notebox.Activities.SECRETDOOR

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.twentytwo.notebox.Activities.SecurePages.devUpate
import com.twentytwo.notebox.R
import kotlinx.android.synthetic.main.activity_developer.*
import kotlinx.android.synthetic.main.activity_developer_update.*

class DeveloperActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_developer)

        secretbtn.setOnClickListener {
            startActivity(Intent(this, LOGINPAGE_SECRET::class.java))
        }
        val db = Firebase.firestore
        val docRef = db.collection("DEVELOPER").document("SECRET")
        docRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Document found in the offline cache
                val document = task.result
                if (document != null) {
                    val users = document.toObject<devUpate>()
                    if (users != null) {
                        linkInsta.text = users.instalink
                        instaFOLOERs.text = users.followers
                        instFG.text = users.following
                        linkFB.text = users.facebooklink
                        linkWEB.text = users.weblink
                    }
                }
            }
        }
    }
}