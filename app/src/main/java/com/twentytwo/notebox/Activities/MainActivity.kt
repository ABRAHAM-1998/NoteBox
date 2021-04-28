package com.twentytwo.notebox.Activities

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.ktx.Firebase
import com.twentytwo.notebox.R
import kotlinx.android.synthetic.main.activity_login.*

class MainActivity : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()


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
        //==============================================================================
    }
    //===============================M=E=N=U============================
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId;
        if (id == R.id.logout_tile) {
            showAlert()
            return true
        } else if(id == R.id.note_profile){
            Toast.makeText(this, "Profile is commming soon", Toast.LENGTH_SHORT).show()
            return true
        }else if(id ==  R.id.note_settings){
            Toast.makeText(this, "settings activity", Toast.LENGTH_SHORT).show()
            return true
        }
        return true
    }
//===================================A=L=E=R=T==L=O==G=O=U=T=====================
    private fun showAlert() {
        lateinit var dialog: AlertDialog
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this)
        alertDialog.setTitle("Note~Box Logout?")
        alertDialog.setMessage("Do you want to logout? \n Fingerprint Authentication will dissable unless you login manually")

        val dilaogOnClickListener = DialogInterface.OnClickListener { _, which ->
            when (which) {
                DialogInterface.BUTTON_NEGATIVE -> Toast.makeText(
                    this,
                    "Dismissed",
                    Toast.LENGTH_SHORT
                ).show()
                DialogInterface.BUTTON_POSITIVE -> {
                    signOut()
                    Toast.makeText(this, "Logout Success", Toast.LENGTH_SHORT).show()
                }
            }
        }
        alertDialog.setPositiveButton("yes", dilaogOnClickListener)
        alertDialog.setNegativeButton("no", dilaogOnClickListener)
        dialog = alertDialog.create()
        dialog.show()
    }
    private fun signOut() {
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
    //============================================================================================

}
