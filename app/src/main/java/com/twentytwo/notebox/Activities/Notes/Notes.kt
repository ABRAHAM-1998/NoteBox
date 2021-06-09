package com.twentytwo.notebox.Activities.Notes

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.twentytwo.notebox.Activities.BdaysActivity.CreateBirthdayActivity
import com.twentytwo.notebox.Activities.SecurePages.notes
import com.twentytwo.notebox.R
import kotlinx.android.synthetic.main.activity_notes.*
import kotlinx.android.synthetic.main.item_noteshead.*

class noteVierwHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class Notes : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)
        floatnotes.setOnClickListener {
            startActivity(Intent(this, Create_Notes::class.java))
        }
        ///////////////////////
        val uid = FirebaseAuth.getInstance().currentUser.uid
        val db = Firebase.firestore

        val query = db.collection("NOTES")
            .whereEqualTo("id", uid)
            .orderBy("created")
        val options = FirestoreRecyclerOptions.Builder<notes>().setQuery(query, notes::class.java)
            .setLifecycleOwner(this).build()

        val adapter = object : FirestoreRecyclerAdapter<notes, noteVierwHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): noteVierwHolder {
                val view =
                    LayoutInflater.from(this@Notes).inflate(R.layout.item_noteshead, parent, false)
                return noteVierwHolder(view)
            }

            override fun onBindViewHolder(holder: noteVierwHolder, position: Int, model: notes) {
                var NoteTitle = holder.itemView.findViewById<TextView>(R.id.NotesTitle)
                var deletebtn = holder.itemView.findViewById<ImageView>(R.id.note_delete)
                var datenote = holder.itemView.findViewById<TextView>(R.id.datenotes)

                var itemnotess = holder.itemView.findViewById<CardView>(R.id.itemnotess)
                NoteTitle.text = model.noteTitle
                datenote.text = model.created

                itemnotess.setOnClickListener{
                    val intent = Intent(this@Notes, NoteViewAcxctivity::class.java)
                    intent.putExtra("title", model.noteTitle)
                    intent.putExtra("content", model.noteContent)
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
        notes_recycle.adapter = adapter
        notes_recycle.layoutManager = LinearLayoutManager(this)
    }
}

