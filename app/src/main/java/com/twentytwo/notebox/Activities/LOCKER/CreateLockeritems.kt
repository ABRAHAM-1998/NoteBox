package com.twentytwo.notebox.Activities.LOCKER

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.twentytwo.notebox.Activities.SecurePages.data_locker
import com.twentytwo.notebox.Firestore.FirestoreClass
import com.twentytwo.notebox.R
import kotlinx.android.synthetic.main.activity_create_lockeritems.*
import kotlinx.android.synthetic.main.cc_contacte_2nd.*
import kotlinx.android.synthetic.main.cc_contacte_2nd.et_mobile
import java.text.SimpleDateFormat

class CreateLockeritems : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_lockeritems)

        ctn_create_locker_items.setOnClickListener {

            val sdf = SimpleDateFormat("dd/M/yyy hh:mm:ss")
            val currentday = sdf.calendar.time.toString()

            val uid = FirebaseAuth.getInstance().currentUser.uid
            val data_locker = currentday?.let { it1 ->
                data_locker(
                    uid,
                    app_name.text.toString().trim(),
                    et_nameLocker.text.toString().trim(),
                    et_email.text.toString().trim(),
                    et_mobile.text.toString().trim(),
                    et_password.text.toString().trim(),
                    it1
                )
            }
            if (data_locker != null) {
                FirestoreClass().CreateLOCkerData(this@CreateLockeritems, data_locker)
            }
        }
    }

    fun Create_LockerSuccess() {

        Toast.makeText(this, "success Success", Toast.LENGTH_SHORT).show()
    }

    fun Create_LockerFailed() {
        Toast.makeText(this, " fsaild Failded to user", Toast.LENGTH_SHORT).show()
    }
}
