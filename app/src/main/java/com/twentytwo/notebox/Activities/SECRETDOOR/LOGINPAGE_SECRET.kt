package com.twentytwo.notebox.Activities.SECRETDOOR

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.twentytwo.notebox.R
import kotlinx.android.synthetic.main.activity_loginpage_secret.*
import java.util.*

class LOGINPAGE_SECRET : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loginpage_secret)
        val SecretCode = "NTY3MjQ2OTc0NTk1NDk0OA=="
        val decodedBytes = Base64.getDecoder().decode(SecretCode)
        val decodedString = String(decodedBytes)


        SECBTN.setOnClickListener {
            val Code = secretKeyBOX.text.toString()

            if (Code == decodedString) {
                startActivity(Intent(this, Developer_Update::class.java))
            } else if (Code.isEmpty()) {
                secretKeyBOX.error = "FUCK OFF"

                return@setOnClickListener
            } else {
                secretKeyBOX.error = "FUCK YOU"

                return@setOnClickListener

            }

        }
    }
}