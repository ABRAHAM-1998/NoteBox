package com.twentytwo.notebox.Activities.IDCARDS

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.twentytwo.notebox.R
import kotlinx.android.synthetic.main.activity_idcards_home.*

class Idcards_Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_idcards_home)
        floatIdButton.setOnClickListener {
            startActivity(Intent(this, Create_Idcards::class.java))

        }
    }
}