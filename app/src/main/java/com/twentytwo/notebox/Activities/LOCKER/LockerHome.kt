package com.twentytwo.notebox.Activities.LOCKER

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.twentytwo.notebox.Activities.SecurePages.data_locker
import com.twentytwo.notebox.R
import kotlinx.android.synthetic.main.activity_contacts.*
import kotlinx.android.synthetic.main.activity_locker_home.*

class Locker_ViewHOLdere(itemView: View) : RecyclerView.ViewHolder(itemView)

class LockerHome : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_locker_home)

        floatlocker.setOnClickListener {
            startActivity(Intent(this, CreateLockeritems::class.java))
        }
        ///////////////////////
        val uid = FirebaseAuth.getInstance().currentUser.uid
        val db = Firebase.firestore

        val query = db.collection("LOCKER")
            .whereEqualTo("id", uid)
            .orderBy("created")

        val options =
            FirestoreRecyclerOptions.Builder<data_locker>()
                .setQuery(query, data_locker::class.java)
                .setLifecycleOwner(this).build()


        val adapter =
            object : FirestoreRecyclerAdapter<data_locker, Locker_ViewHOLdere>(options) {
                override fun onCreateViewHolder(
                    parent: ViewGroup,
                    viewType: Int
                ): Locker_ViewHOLdere {
                    val view = LayoutInflater.from(this@LockerHome)
                        .inflate(R.layout.item_locker, parent, false)
                    return Locker_ViewHOLdere(view)
                }

                override fun onBindViewHolder(
                    holder: Locker_ViewHOLdere,
                    position: Int,
                    model: data_locker
                ) {
                    var appname = holder.itemView.findViewById<TextView>(R.id.lk_app_name)
                    var name = holder.itemView.findViewById<TextView>(R.id.lk_item_name)
                    var mobile = holder.itemView.findViewById<TextView>(R.id.lk_mobile)
                    var email = holder.itemView.findViewById<TextView>(R.id.lk_email)
                    var password = holder.itemView.findViewById<TextView>(R.id.lk_pass)
                    var deletebtn = holder.itemView.findViewById<ImageView>(R.id.ct_delete)
                    var editvtn = holder.itemView.findViewById<ImageView>(R.id.ct_edit)
                    appname.text = model.app_name
                    name.text = model.usename
                    mobile.text = model.mobile
                    email.text = model.email
                    password.text = model.password
                    mobile.text = model.mobile

                    deletebtn.setOnClickListener {
                        deleteItem(position)
                    }
                }

                fun deleteItem(positionr: Int) {
                    snapshots.getSnapshot(positionr).reference.delete()
                    notifyItemRemoved(positionr)
                }
            }

        LockerREecycler.adapter = adapter
        LockerREecycler.layoutManager = LinearLayoutManager(this)
    }
}
