package com.twentytwo.notebox.Activities.Notes

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
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
            Toast.makeText(this, "buttonb cliclered", Toast.LENGTH_SHORT).show()
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

        Toast.makeText(this, "noters success Success", Toast.LENGTH_SHORT).show()
    }

    fun CreateNotesfails() {
        Toast.makeText(this, "notes fsaild Failded to user", Toast.LENGTH_SHORT).show()
    }
}