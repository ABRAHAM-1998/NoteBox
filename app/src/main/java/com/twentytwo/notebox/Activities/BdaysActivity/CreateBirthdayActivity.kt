package com.twentytwo.notebox.Activities.BdaysActivity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.google.firebase.auth.FirebaseAuth
import com.twentytwo.notebox.Activities.SecurePages.bdaydata
import com.twentytwo.notebox.Firestore.FirestoreClass
import com.twentytwo.notebox.R
import kotlinx.android.synthetic.main.activity_create_birthday.*
import kotlinx.android.synthetic.main.activity_signup.*
import java.text.SimpleDateFormat

class CreateBirthdayActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_birthday)
        @Suppress("DEPRECATION")
//        var data: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
//
//        var bdtats = data.getBoolean("sync",true)
//        Toast.makeText(this, "$bdtats", Toast.LENGTH_SHORT).show()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
            )
        }

        if (intent.getStringExtra("name") != null) {
            cb_name.setText(intent.getStringExtra("name"))
            cb_remiderSwitch.isChecked = intent.getBooleanExtra("notyfy", false)
            cb_create_button.text = "Update"
            cb_1.text = "UPDATE BIRTHDAY EVENT"

            cb_create_button.setOnClickListener {
                if (cb_name.text.isEmpty()){
                    cb_name.error="NAME MUST"
                    return@setOnClickListener
                }
                val day = cb_calenderView.dayOfMonth.toString().trim()
                val month = (cb_calenderView.month + 1).toString().trim()
                val year = cb_calenderView.year.toString().trim()
                val sdf = SimpleDateFormat("dd/M/yyy hh:mm:ss")
                val currentday = intent.getStringExtra("created")

                val uid = FirebaseAuth.getInstance().currentUser.uid
                val bdaydata = currentday?.let { it1 ->
                    bdaydata(
                        uid,
                        cb_name.text.toString().trim(),
                        "$day-$month-$year",
                        cb_remiderSwitch.isChecked,
                        it1
                    )
                }
                if (bdaydata != null) {
                    FirestoreClass().UpdateBday(this@CreateBirthdayActivity, bdaydata)
                }
            }

        } else {
            cb_create_button.setOnClickListener {
                if (cb_name.text.isEmpty()){
                    cb_name.error="NAME MUST"
                    return@setOnClickListener
                }
                val day = cb_calenderView.dayOfMonth.toString().trim()
                val month = (cb_calenderView.month + 1).toString().trim()
                val year = cb_calenderView.year.toString().trim()
                val sdf = SimpleDateFormat("dd/M/yyy hh:mm:ss")
                val currentday = sdf.calendar.time.toString()

                val uid = FirebaseAuth.getInstance().currentUser.uid
                val bdaydata = bdaydata(
                    uid,
                    cb_name.text.toString().trim(),
                    "$day-$month-$year",
                    cb_remiderSwitch.isChecked,
                    currentday
                )
                FirestoreClass().CreatwBirthday(this@CreateBirthdayActivity, bdaydata)

            }
        }
    }


    fun CreateBdaySuccess() {

        Toast.makeText(this, "Bdsay success Success", Toast.LENGTH_SHORT).show()

        val intent = Intent(this, BirthdayActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
    }

    fun CreateBdayfailure() {
        Toast.makeText(this, "bday fsaild Failded to user", Toast.LENGTH_SHORT).show()
    }
}
