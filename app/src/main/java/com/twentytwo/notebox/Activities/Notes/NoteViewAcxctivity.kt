package com.twentytwo.notebox.Activities.Notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.twentytwo.notebox.R
import kotlinx.android.synthetic.main.activity_create_birthday.*
import kotlinx.android.synthetic.main.activity_note_view_acxctivity.*

class NoteViewAcxctivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_view_acxctivity)

        if (intent.getStringExtra("title") != null) {
            noteviewhead.setText(intent.getStringExtra("title"))
            noteviewcontent.setText(intent.getStringExtra("content"))
        }
    }
}