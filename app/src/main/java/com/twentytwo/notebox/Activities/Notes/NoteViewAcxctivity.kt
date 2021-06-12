package com.twentytwo.notebox.Activities.Notes

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.twentytwo.notebox.R
import kotlinx.android.synthetic.main.activity_note_view_acxctivity.*

class NoteViewAcxctivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_view_acxctivity)

        floatNotesView.setOnClickListener {
            val texttocopy = noteviewcontent.text
            val clipboardmanager = getSystemService(Context.CLIPBOARD_SERVICE)as ClipboardManager
            val clipData = ClipData.newPlainText("text",texttocopy)
            clipboardmanager.setPrimaryClip(clipData)
            Toast.makeText(this, "TEXT COPIED TO CLIPBOARD", Toast.LENGTH_SHORT).show()
        }

        if (intent.getStringExtra("title") != null) {
            noteviewhead.setText(intent.getStringExtra("title"))
            noteviewcontent.setText(intent.getStringExtra("content"))
        }
    }
}