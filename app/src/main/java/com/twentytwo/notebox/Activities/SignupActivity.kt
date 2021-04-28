package com.twentytwo.notebox.Activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.twentytwo.notebox.Firestore.FirestoreClass
import com.twentytwo.notebox.Models.User
import com.twentytwo.notebox.R
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_signup.*
import java.text.SimpleDateFormat
import java.util.*


class SignupActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        auth = FirebaseAuth.getInstance()

        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)

        radioGroup?.setOnCheckedChangeListener { group, checkedId ->
            val gendertext = if (R.id.male_btn == checkedId) "male" else "female"
            Toast.makeText(applicationContext, gendertext, Toast.LENGTH_SHORT).show()
        }
        //=====================FULL-SCREEN===========================
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
            )
        }
        //=============================r====R=E=G=I=S=T=E=R=I=N=G==================
        registerButon.setOnClickListener {
            if (sgn_name.text.toString().isEmpty()) {
                sgn_name.error = "please enter name"
                sgn_name.requestFocus()
                return@setOnClickListener
            } else if (sgn_name.text.toString().length <= 3) {
                sgn_name.error = "please enter valid name"
                sgn_name.requestFocus()
                return@setOnClickListener
            }
            if (sgn_email.text.toString().isEmpty()) {
                sgn_email.error = "please enter email"
                sgn_email.requestFocus()
                return@setOnClickListener
            }
            if (sgn_phone.text.toString().isEmpty()) {
                sgn_phone.error = "please enter sgn_phone"
                sgn_phone.requestFocus()
                return@setOnClickListener
            } else if (sgn_phone.text.toString().length != 10) {
                sgn_phone.error = "Invalid Phone"
                sgn_phone.requestFocus()
                return@setOnClickListener
            }
            if (inputt_pass1.text.toString().isEmpty()) {
                inputt_pass1.error = "please enter inputt_pass1"
                inputt_pass1.requestFocus()
                return@setOnClickListener
            } else if (inputt_pass1.text.toString().length <= 5) {
                inputt_pass1.error = "please ente atleast 6 char"
                inputt_pass1.requestFocus()
                return@setOnClickListener
            }
            if (inputt_pass2.text.toString().isEmpty()) {
                inputt_pass2.error = "please enter inputt_pass2"
                inputt_pass1.requestFocus()
                return@setOnClickListener
            } else if (inputt_pass2.text.toString() != inputt_pass1.text.toString()) {
                inputt_pass2.error = "Passwords donot match!!"
                inputt_pass1.requestFocus()
                return@setOnClickListener
            }
            auth.createUserWithEmailAndPassword(
                sgn_email.text.toString(),
                inputt_pass2.text.toString()
            )
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        //===================================
                        val sdf = SimpleDateFormat("dd/M/yyy hh:mm:ss")
                        val currentday = sdf.format(Date())


                        val fireebaseUser: FirebaseUser = task.result?.user!!
                        val userdetails = User(
                            fireebaseUser.uid,
                            sgn_name.text.toString().trim(),
                            sgn_email.text.toString().trim(),
                            sgn_phone.text.toString().trim(),
                            inputt_pass2.text.toString().trim(),
                            currentday.toString()
                        )
                        FirestoreClass().registeraUser(this@SignupActivity, userdetails)
                        //-------------------
                        //============================/==============EMAIL-VERI=============================
                        user?.sendEmailVerification()
                            ?.addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    sign_llogTxt.text =
                                        "AN EMAIL HASBEEN SEND TO YOUR EMAIL ADDRESS" +
                                                "\n PLESASE VERYFY TO LOGIN"
                                } else {
                                    sign_llogTxt.text = "UNABLE TO SEND EMAIL VERIFICATION"
                                }
                            }
                    } else {
                        sign_llogTxt.text = "Sign Up failed. Try again after some time." +
                                "\n EMAIL ALREADY IN USE! OR UNABLE TO SEMD EMAIL ADDRESS"
                        Toast.makeText(
                            baseContext, "Sign Up failed. Try again after some time." +
                                    "\n EMAIL ALREADY IN USE! OR UNABLE TO SEMD EMAIL ADDRESS",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }

    //--------------------------------------------------------------------
    fun userRegistrationSuccess() {
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
        Toast.makeText(this, "UserRegistration Success", Toast.LENGTH_SHORT).show()
    }

    fun userRegistrationFailure() {
        sign_llogTxt.text = "USER REGISTRATION FAILED!"
        Toast.makeText(this, "UserRegistration Failded to user", Toast.LENGTH_SHORT).show()
    }
}



