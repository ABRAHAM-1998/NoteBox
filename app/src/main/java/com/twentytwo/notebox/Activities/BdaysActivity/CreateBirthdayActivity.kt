package com.twentytwo.notebox.Activities.BdaysActivity

import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
            )
        }

        if (intent.getStringExtra("name") != null) {
            Toast.makeText(this, "${intent.getStringExtra("name")}", Toast.LENGTH_SHORT).show()
            cb_name.setText(intent.getStringExtra("name"))
            cb_remiderSwitch.isChecked = intent.getBooleanExtra("notyfy", false)
            cb_create_button.text = "Update"
            cb_1.text = "UPDATE BIRTHDAY EVENT"

            cb_create_button.setOnClickListener {
                cb_create_button.setOnClickListener {
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
            }
        } else {
            cb_create_button.setOnClickListener {
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
        }

        fun CreateBdayfailure() {
            Toast.makeText(this, "bday fsaild Failded to user", Toast.LENGTH_SHORT).show()
        }
    }
