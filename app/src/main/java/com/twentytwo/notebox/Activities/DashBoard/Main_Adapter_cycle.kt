package com.twentytwo.notebox.Activities.DashBoard

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.twentytwo.notebox.Activities.BdaysActivity.BirthdayActivity
import com.twentytwo.notebox.Activities.CERTIFICATES.CertificatesActivity
import com.twentytwo.notebox.Activities.CONTACTS.Contacts
import com.twentytwo.notebox.Activities.IDCARDS.Idcards_Home
import com.twentytwo.notebox.Activities.LOCKER.LockerLoginPage
import com.twentytwo.notebox.Activities.Notes.Notes
import com.twentytwo.notebox.Activities.SecurePages.LOCK_DATA
import com.twentytwo.notebox.R

class Main_Adapter_cycle(
    private val contex: Context,
    private val main_data_list: ArrayList<recycler_mainDataClass>
) : RecyclerView.Adapter<Main_Adapter_cycle.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titles = itemView.findViewById<TextView>(R.id.main_rec_btn)
        var main_rec_img = itemView.findViewById<ImageView>(R.id.main_rec_logo)


    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(contex).inflate(R.layout.main_recycler_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val maindata: recycler_mainDataClass = main_data_list.get(position)
        holder.titles.text = maindata.title_item
        holder.main_rec_img.setImageResource(maindata.icons!!)
        holder.titles.textSize = maindata.size.toFloat()

        holder.titles.setOnClickListener {
            funItemTouched(position)
        }
        holder.main_rec_img.setOnClickListener {
            funItemTouched(position)
        }
    }

    private fun funItemTouched(position: Int) {


        if (position == 0) {
            val intent = Intent(contex, BirthdayActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            contex.startActivity(intent)
        }
        if (position == 1) {
            val intent = Intent(contex, Contacts::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            contex.startActivity(intent)
        }
        if (position == 2) {
            val intent = Intent(contex, Notes::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            contex.startActivity(intent)
        }
        if (position == 3) {
            val intent = Intent(contex, LockerLoginPage::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            contex.startActivity(intent)
        }
        if (position == 4) {
            var prefData: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(contex)
            val lock = prefData.getBoolean("numpass", false)
            if (lock) {
                val intent = Intent(contex, LOCK_DATA::class.java)
                intent.putExtra("name", "certificates")
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                contex.startActivity(intent)
            } else {
                val intent = Intent(contex, CertificatesActivity::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                contex.startActivity(intent)
            }
        }
        if (position == 5) {
            var prefData: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(contex)
            val lock = prefData.getBoolean("numpass", false)
            if (lock) {
                val intent = Intent(contex, LOCK_DATA::class.java)
                intent.putExtra("name", "idcards")
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                contex.startActivity(intent)
            } else {

                val intent = Intent(contex, Idcards_Home::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                contex.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return main_data_list.size
    }
}