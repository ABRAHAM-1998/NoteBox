package com.twentytwo.notebox.Activities.IDCARDS

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import com.twentytwo.notebox.Activities.CERTIFICATES.ImgViewHolder
import com.twentytwo.notebox.Activities.SecurePages.certific
import com.twentytwo.notebox.R
import kotlinx.android.synthetic.main.activity_certificates.*
import kotlinx.android.synthetic.main.activity_idcards_home.*

class ImgViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class Idcards_Home : AppCompatActivity() {

    private var mAdapter: FirebaseRecyclerAdapter<certific, ImgViewHolder>? = null

    internal var storage: FirebaseStorage? = null
    internal var storageReference: StorageReference? = null
    val storageRef = storage?.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_idcards_home)

        floatIdButton.setOnClickListener {
            startActivity(Intent(this, Create_Idcards::class.java))

        }

        storage = FirebaseStorage.getInstance()
        storageReference = storage!!.reference

        val uid = FirebaseAuth.getInstance().currentUser.uid
        val db = Firebase.firestore

        val query = db.collection("IDCARDS")
            .whereEqualTo("id", uid)
            .orderBy("created")


        val options = FirestoreRecyclerOptions.Builder<certific>()
            .setQuery(query, certific::class.java)
            .setLifecycleOwner(this)
            .build()
        val adapter =
            object : FirestoreRecyclerAdapter<certific, ImgViewHolder>(options) {
                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImgViewHolder {
                    val view = LayoutInflater.from(this@Idcards_Home)
                        .inflate(R.layout.item_image, parent, false)
                    return ImgViewHolder(view)
                }

                override fun onBindViewHolder(
                    holder: ImgViewHolder,
                    position: Int,
                    model: certific
                ) {
                    var name = holder.itemView.findViewById<TextView>(R.id.tvImgName)
                    var hpplink = holder.itemView.findViewById<TextView>(R.id.hpplink)
                    var downloadbtn = holder.itemView.findViewById<Button>(R.id.button)
                    var deletebtn = holder.itemView.findViewById<ImageView>(R.id.deleteCert)
                    var date = holder.itemView.findViewById<TextView>(R.id.DateCreated)
                    var filename = holder.itemView.findViewById<TextView>(R.id.fileName)




                    name.text = model.descreption.toString()
                    date.text = model.created.toString()
                    filename.text = model.filename.toString()


                    Picasso.with(this@Idcards_Home)
                        .load(model.imgUrl.toString())
                        .into(holder.itemView.findViewById<ImageView>(R.id.imgView))



                    downloadbtn.setOnClickListener {
                        val storage = FirebaseStorage.getInstance()
                        val storageRef = storage.reference
                        storageRef.child("IDCARDS/${model.filename}").downloadUrl.addOnSuccessListener {
                            hpplink.text = it.toString()
                        }
                    }
                    deletebtn.setOnClickListener {
                        val storage = FirebaseStorage.getInstance()
                        val storageRef = storage.reference
                        val deleteRef = storageRef.child("IDCARDS/${model.filename}")
                        deleteRef.delete().addOnSuccessListener {

                            snapshots.getSnapshot(position).reference.delete()
                            notifyItemRemoved(position)
                            Toast.makeText(this@Idcards_Home, "DELETED", Toast.LENGTH_SHORT)
                                .show()

                        }.addOnFailureListener {
                            Toast.makeText(
                                this@Idcards_Home,
                                "FAILED TO DELETE",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }


            }
        idcard_recycler.adapter = adapter
        idcard_recycler.layoutManager = LinearLayoutManager(this)
    }
}