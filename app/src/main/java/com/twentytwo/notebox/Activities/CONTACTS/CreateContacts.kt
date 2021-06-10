package com.twentytwo.notebox.Activities.CONTACTS

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.twentytwo.notebox.Activities.BdaysActivity.BirthdayActivity
import com.twentytwo.notebox.Activities.SecurePages.datacontacts
import com.twentytwo.notebox.Firestore.FirestoreClass
import com.twentytwo.notebox.R
import kotlinx.android.synthetic.main.activity_create_birthday.*
import kotlinx.android.synthetic.main.cc_contacte_2nd.*
import java.text.SimpleDateFormat

class CreateContacts : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_contacts)

        btn_creatContacts.setOnClickListener {

            val sdf = SimpleDateFormat("dd/M/yyy hh:mm:ss")
            val currentday = sdf.calendar.time.toString()

            val uid = FirebaseAuth.getInstance().currentUser.uid
            val contactData = currentday?.let { it1 ->
                datacontacts(
                    uid,
                    et_name.text.toString().trim(),
                    et_cc_email.text.toString().trim(),
                    et_mobile.text.toString().trim(),
                    et_address.text.toString().trim(),
                    it1
                )
            }
            if (contactData != null) {
                FirestoreClass().CreateContacts(this@CreateContacts, contactData)
            }
        }
    }

    fun CC_ContractsSucurd() {
        val intent = Intent(this, Contacts::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)

        Toast.makeText(this, "Bdsay success Success", Toast.LENGTH_SHORT).show()
    }

    fun CC_Failed() {
        Toast.makeText(this, "bday fsaild Failded to user", Toast.LENGTH_SHORT).show()
    }
}
