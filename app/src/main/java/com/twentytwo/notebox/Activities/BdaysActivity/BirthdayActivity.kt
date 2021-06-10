package com.twentytwo.notebox.Activities.BdaysActivity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.twentytwo.notebox.R
import kotlinx.android.synthetic.main.activity_birthday.*
import kotlinx.android.synthetic.main.item__birthday.*


data class B_data(
    val names: String = "",
    var date: String = "",
    val remind: Boolean = false,
    val created: String = ""
)

class Bday_ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class BirthdayActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_birthday)


        ///////////////////////
        val uid = FirebaseAuth.getInstance().currentUser.uid
        val db = Firebase.firestore

        val query = db.collection("BIRTHDAYS")
            .whereEqualTo("id", uid)
            .orderBy("created")

        val options = FirestoreRecyclerOptions.Builder<B_data>().setQuery(query, B_data::class.java)
            .setLifecycleOwner(this).build()


        val adapter = object : FirestoreRecyclerAdapter<B_data, Bday_ViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Bday_ViewHolder {
                val view = LayoutInflater.from(this@BirthdayActivity)
                    .inflate(R.layout.item__birthday, parent, false)

                return Bday_ViewHolder(view)

            }

            @SuppressLint("UseSwitchCompatOrMaterialCode")
            override fun onBindViewHolder(holder: Bday_ViewHolder, position: Int, model: B_data) {
                var Bd_name = holder.itemView.findViewById<TextView>(R.id.bday_name)
                var Bd_date = holder.itemView.findViewById<TextView>(R.id.bday_date)
                var remind_me = holder.itemView.findViewById<ImageView>(R.id.bday_switch)
                var deletebtn = holder.itemView.findViewById<ImageView>(R.id.ct_delete)
                var editvtn = holder.itemView.findViewById<ImageView>(R.id.ct_edit)

                Bd_name.text = model.names
                Bd_date.text = model.date

                if (model.remind == true) {
                    remind_me.setColorFilter(Color.parseColor("#FF4FF10A"))
                }

                editvtn.setOnClickListener {
                    val intent = Intent(this@BirthdayActivity, CreateBirthdayActivity::class.java)
                    intent.putExtra("name", model.names)
                    intent.putExtra("notyfy", model.remind)
                    intent.putExtra("created", model.created)
                    startActivity(intent)
                }

                deletebtn.setOnClickListener {
                    deleteItem(position)

                }
            }

            fun deleteItem(positionr: Int) {
                snapshots.getSnapshot(positionr).reference.delete()
                notifyItemRemoved(positionr)

            }

        }
        birthday_recyclerView.adapter = adapter
        birthday_recyclerView.layoutManager = LinearLayoutManager(this)
        //=====================================================

        floatingActionButton.setOnClickListener {
            startActivity(Intent(this, CreateBirthdayActivity::class.java))
        }
    }


}