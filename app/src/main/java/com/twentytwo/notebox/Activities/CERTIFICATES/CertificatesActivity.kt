package com.twentytwo.notebox.Activities.CERTIFICATES

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.twentytwo.notebox.Activities.Notes.Create_Notes
import com.twentytwo.notebox.R
import kotlinx.android.synthetic.main.activity_certificates.*
import kotlinx.android.synthetic.main.activity_notes.*

class CertificatesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_certificates)
        floatCertificates.setOnClickListener {
            startActivity(Intent(this, CreateCertificates::class.java))
        }
    }
}