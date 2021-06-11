package com.twentytwo.notebox.Activities.CERTIFICATES

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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
import com.twentytwo.notebox.Activities.SecurePages.certific
import com.twentytwo.notebox.R
import kotlinx.android.synthetic.main.activity_certificates.*
import kotlinx.android.synthetic.main.activity_contacts.*
import kotlinx.android.synthetic.main.activity_notes.*
import kotlinx.android.synthetic.main.item_image.*
import kotlinx.android.synthetic.main.item_image.view.*


class ImgViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class CertificatesActivity : AppCompatActivity() {

    private var mAdapter: FirebaseRecyclerAdapter<certific, ImgViewHolder>? = null

    internal var storage: FirebaseStorage? = null
    internal var storageReference: StorageReference? = null
    val storageRef = storage?.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_certificates)
        floatCertificates.setOnClickListener {
            startActivity(Intent(this, CreateCertificates::class.java))

        }

        setupPermissions()



        storage = FirebaseStorage.getInstance()
        storageReference = storage!!.reference

        val uid = FirebaseAuth.getInstance().currentUser.uid
        val db = Firebase.firestore

        val query = db.collection("CERTIFICATES")
            .whereEqualTo("id", uid)
            .orderBy("created")


        val options = FirestoreRecyclerOptions.Builder<certific>()
            .setQuery(query, certific::class.java)
            .setLifecycleOwner(this)
            .build()

        val adapter =
            object : FirestoreRecyclerAdapter<certific, ImgViewHolder>(options) {
                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImgViewHolder {
                    val view = LayoutInflater.from(this@CertificatesActivity)
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


                    name.text = model.descreption.toString()

                    Picasso.with(this@CertificatesActivity)
                        .load(model.imgUrl.toString())
                        .into(holder.itemView.findViewById<ImageView>(R.id.imgView))



                    downloadbtn.setOnClickListener {
                        val storage = FirebaseStorage.getInstance()
                        val storageRef = storage.reference
                        storageRef.child("CERTIFICATES/${model.filename}").downloadUrl.addOnSuccessListener {
                            hpplink.text = it.toString()
                        }
                    }
                    deletebtn.setOnClickListener {
                        val storage = FirebaseStorage.getInstance()
                        val storageRef = storage.reference
                        val deleteRef = storageRef.child("CERTIFICATES/${model.filename}")
                        deleteRef.delete().addOnSuccessListener {

                            snapshots.getSnapshot(position).reference.delete()
                            notifyItemRemoved(position)
                            Toast.makeText(this@CertificatesActivity, "DELETED", Toast.LENGTH_SHORT).show()

                        }.addOnFailureListener {
                            Toast.makeText(
                                this@CertificatesActivity,
                                "Failed to delete",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }


            }
        rcvListImg.adapter = adapter
        rcvListImg.layoutManager = LinearLayoutManager(this)
    }

    private fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        if (permission != PackageManager.PERMISSION_GRANTED) {
            Log.i("TAAAG", "Permission to record denied")
        } else {
            Log.i("TAAAG", "Permission to record assess")

        }
    }
}

