package com.twentytwo.notebox.Activities.SECRETDOOR

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.twentytwo.notebox.Activities.LOCKER.UserList
import com.twentytwo.notebox.Activities.SecurePages.devUpate
import com.twentytwo.notebox.Firestore.FirestoreClass
import com.twentytwo.notebox.R
import kotlinx.android.synthetic.main.activity_developer_update.*

class Developer_Update : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_developer_update)

        secUserDev.setOnClickListener {
            startActivity(Intent(this,Users_List_Dev::class.java))
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
                        FG.setText(users.following)
                        FS.setText(users.followers)
                        INSTAID.setText(users.instalink)
                        FBID.setText(users.facebooklink)
                        WEBID.setText(users.weblink)
                    }
                }
            }
        }
        devUpdateBtn.setOnClickListener {
            val data = devUpate(
                FS.text.toString(),
                FG.text.toString(),
                INSTAID.text.toString(),
                FBID.text.toString(),
                WEBID.text.toString()
            )

            FirestoreClass().CreateDEV(this@Developer_Update, data)

        }
    }

    fun DevFAILED() {
        Toast.makeText(this, "FAILEDD", Toast.LENGTH_SHORT).show()
    }

    fun DevSUCCESS() {
        Toast.makeText(this, "SUCCESS", Toast.LENGTH_SHORT).show()
    }
}