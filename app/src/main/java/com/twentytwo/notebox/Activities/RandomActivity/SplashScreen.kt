package com.twentytwo.notebox.Activities.RandomActivity

import android.content.Context
import android.content.Intent
import android.os.Build
import android.app.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.view.animation.AnimationUtils
import com.twentytwo.notebox.Activities.SecurePages.LoginActivity
import com.twentytwo.notebox.R
import kotlinx.android.synthetic.main.activity_splash_screen.*
import java.util.*

class SplashScreen : AppCompatActivity() {
    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        //=============================ANIMATION============================
        val animation = AnimationUtils.loadAnimation(applicationContext, R.anim.blink)
        splash_text1.startAnimation(animation)
        splashDevtxt.startAnimation(animation)



//---------------------------------------------------------------------------
        //=====================FULL-SCREEN===========================
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        }else{
            window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN,)
        }
        //--------------------------------------------------------------------
        //==========================TIMER===========================
        Timer().schedule( object : TimerTask(){
            override fun run() {
                startActivity(Intent(this@SplashScreen, LoginActivity::class.java))
                finish()

            }
        },1500L )
        //=--------------------------------------------------------------------
    }
}