package com.twentytwo.notebox.Activities.Notes

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.twentytwo.notebox.Activities.BdaysActivity.BirthdayActivity
import com.twentytwo.notebox.Activities.SecurePages.notes
import com.twentytwo.notebox.Firestore.FirestoreClass
import com.twentytwo.notebox.R
import kotlinx.android.synthetic.main.activity_create__notes.*
import java.text.SimpleDateFormat

class Create_Notes : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create__notes)
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
            )
        }
        note_create_button.setOnClickListener{

            if (nc__et_head.text.isEmpty()){
                nc__et_head.error="ENTER TITLE"
                return@setOnClickListener
            }
            val sdf = SimpleDateFormat("dd/M/yyy hh:mm:ss")
            val currentday = sdf.calendar.time.toString()
            val uid = FirebaseAuth.getInstance().currentUser.uid
            val notetitle = nc__et_head.text.toString()
            val noteContent = cn__notesContent.text.toString()
            val notesData = currentday?.let { it ->
                notes(uid,notetitle,noteContent,it)
            }

            if (notesData != null) {
                FirestoreClass().CreateNotes(this@Create_Notes,notesData)
            }
        }

    }

    fun CreateNotesSuccess() {
        val intent = Intent(this, Notes::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)

        Toast.makeText(this, "SUCCESSFULLY CREATED", Toast.LENGTH_SHORT).show()
    }

    fun CreateNotesfails() {
        Toast.makeText(this, "FAILED TO CREATE", Toast.LENGTH_SHORT).show()
    }
}