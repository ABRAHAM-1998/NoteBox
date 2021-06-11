package com.twentytwo.notebox.Activities.DashBoard

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.twentytwo.notebox.Activities.SECRETDOOR.DeveloperActivity
import com.twentytwo.notebox.Activities.SecurePages.LoginActivity
import com.twentytwo.notebox.Activities.SecurePages.ProfileActivity
import com.twentytwo.notebox.R

class MainActivity : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth
    //================
    private var reccyclerViewMain: RecyclerView? = null
    private var charItem: ArrayList<recycler_mainDataClass>? = null
    private var gridLayoutManager: GridLayoutManager? = null
    private var alphaAdapters: Main_Adapter_cycle? = null
    ///////////////////////

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()


        //=====================FULL-SCREEN===========================
//        @Suppress("DEPRECATION")
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            window.insetsController?.hide(WindowInsets.Type.statusBars())
//        } else {
//            window.setFlags(
//                WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN,
//            )
//        }
        //==============================================================================
        //============================R=R=EC=Y=C=L=E=R===V=I=E=W=====================
       reccyclerViewMain  = findViewById(R.id.main_recyclerView)
        gridLayoutManager =
            GridLayoutManager(applicationContext, 2, LinearLayoutManager.VERTICAL, false)
        reccyclerViewMain?.layoutManager = gridLayoutManager
        reccyclerViewMain?.setHasFixedSize(true)

        charItem = ArrayList()
        charItem = setAlphas()
        alphaAdapters = Main_Adapter_cycle(applicationContext, charItem!!)
        reccyclerViewMain?.adapter = alphaAdapters

    }

    private fun setAlphas(): ArrayList<recycler_mainDataClass>? {
        var arrayList: ArrayList<recycler_mainDataClass> = ArrayList()
        arrayList.add(recycler_mainDataClass("B'DAYS \n jgjh",R.drawable.bday,15))
        arrayList.add(recycler_mainDataClass("CONTACTS",R.drawable.contacts,20))
        arrayList.add(recycler_mainDataClass("NOTES",R.drawable.notes,20))
        arrayList.add(recycler_mainDataClass("NOTE~BOX \n LOCKER",R.drawable.locker,20))
        arrayList.add(recycler_mainDataClass("CERTIFICATES",R.drawable.certificate,20))
        arrayList.add(recycler_mainDataClass("IDENTITY \n CARDS",R.drawable.cards,20))


        return  arrayList
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
            startActivity(Intent(this,ProfileActivity::class.java))
            Toast.makeText(this, "Profile is commming soon", Toast.LENGTH_SHORT).show()
            return true
        }else if(id ==  R.id.note_settings){
            Toast.makeText(this, "settings activity", Toast.LENGTH_SHORT).show()
            return true
        }else if(id ==  R.id.developerid){
            Toast.makeText(this, "Developer activity", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, DeveloperActivity::class.java))
            return true
        }
        return true
    }
//===================================A=L=E=R=T==L=O==G=O=U=T=====================
    private fun showAlert() {
        lateinit var dialog: AlertDialog
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this)
        alertDialog.setTitle("Note~Box?")
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
