package com.twentytwo.notebox.Activities.CONTACTS

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
import com.twentytwo.notebox.Activities.SecurePages.datacontacts
import com.twentytwo.notebox.R
import kotlinx.android.synthetic.main.activity_contacts.*




class contacts_ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class Contacts : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)

        float_conytacts.setOnClickListener {
            startActivity(Intent(this, CreateContacts::class.java))
        }


        ///////////////////////
        val uid = FirebaseAuth.getInstance().currentUser.uid
        val db = Firebase.firestore

        val query = db.collection("CONTACTS")
            .whereEqualTo("id", uid)
            .orderBy("created")

        val options =
            FirestoreRecyclerOptions.Builder<datacontacts>()
                .setQuery(query, datacontacts::class.java)
                .setLifecycleOwner(this).build()


        val adapter =
            object : FirestoreRecyclerAdapter<datacontacts, contacts_ViewHolder>(options) {
                override fun onCreateViewHolder(
                    parent: ViewGroup,
                    viewType: Int
                ): contacts_ViewHolder {
                    val view = LayoutInflater.from(this@Contacts)
                        .inflate(R.layout.item_contacts, parent, false)
                    return contacts_ViewHolder(view)
                }

                override fun onBindViewHolder(
                    holder: contacts_ViewHolder,
                    position: Int,
                    model: datacontacts
                ) {
                    var name = holder.itemView.findViewById<TextView>(R.id.cts_name)
                    var mobile = holder.itemView.findViewById<TextView>(R.id.cts_mobile)
                    var email = holder.itemView.findViewById<TextView>(R.id.cts_email)
                    var address = holder.itemView.findViewById<TextView>(R.id.cts_address)
                    var deletebtn = holder.itemView.findViewById<ImageView>(R.id.ct_delete)
                    var editvtn = holder.itemView.findViewById<ImageView>(R.id.ct_edit)

                    name.text = model.name
                    mobile.text = model.mobile
                    email.text = model.email
                    address.text = model.address

                    deletebtn.setOnClickListener {
                        deleteItem(position)
                    }
                }

                fun deleteItem(positionr: Int) {
                    snapshots.getSnapshot(positionr).reference.delete()
                    notifyItemRemoved(positionr)
                }
            }
        contact_recyclerview.adapter = adapter
        contact_recyclerview.layoutManager = LinearLayoutManager(this)
    }
}

