package com.twentytwo.notebox.Activities.SECRETDOOR

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.twentytwo.notebox.R
import kotlinx.android.synthetic.main.activity_notes.*
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_users_list_dev.*


data class UserListDev(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val mobile: String = "",
    val lastlogin: String = "",
    val lkeystatus: Boolean = false

)

class devViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
class Users_List_Dev : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users_list_dev)
        val db = Firebase.firestore
        val uid = FirebaseAuth.getInstance().currentUser.uid


        val query = db.collection("UsersDetails")
            .orderBy("lastlogin")
        val options = FirestoreRecyclerOptions.Builder<UserListDev>()
            .setQuery(query, UserListDev::class.java)
            .setLifecycleOwner(this).build()


        val adapter = object : FirestoreRecyclerAdapter<UserListDev, devViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): devViewHolder {
                val view =
                    LayoutInflater.from(this@Users_List_Dev)
                        .inflate(R.layout.item_dev_user, parent, false)
                return devViewHolder(view)
            }

            override fun onBindViewHolder(
                holder: devViewHolder,
                position: Int,
                model: UserListDev
            ) {
                var usrEmail = holder.itemView.findViewById<TextView>(R.id.usrEmail)
                var usName = holder.itemView.findViewById<TextView>(R.id.usrName)
                var usrLastseen = holder.itemView.findViewById<TextView>(R.id.usrLastSeen)
                var lkStatus = holder.itemView.findViewById<TextView>(R.id.ustLkStatus)


                usrEmail.text = "EMAIL  : ${model.email}"
                usName.text = "NAME  :${model.name}"
                usrLastseen.text = "LASTSEEN : ${model.lastlogin}"
//                lkStatus.text  = "LOCKER : ${model.lkeystatus}"
                if (model.lkeystatus == true){
                    lkStatus.text = "LOCKER : ${model.lkeystatus}"
                    lkStatus.setTextColor(Color.GREEN)
                }else
                    lkStatus.text = "LOCKER : ${model.lkeystatus}"

            }
        }
        recyc_users.adapter = adapter
        recyc_users.layoutManager = LinearLayoutManager(this)

    }
}