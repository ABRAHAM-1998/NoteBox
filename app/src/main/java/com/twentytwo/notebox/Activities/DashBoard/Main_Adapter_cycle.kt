package com.twentytwo.notebox.Activities.DashBoard

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.twentytwo.notebox.Activities.BdaysActivity.BirthdayActivity
import com.twentytwo.notebox.Activities.CONTACTS.Contacts
import com.twentytwo.notebox.Activities.LOCKER.LockerLoginPage
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
        } else {
            Toast.makeText(contex, "do nothiinng itda ann errfor", Toast.LENGTH_SHORT).show()

        }
        if (position == 1) {
            val intent = Intent(contex, Contacts::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            contex.startActivity(intent)
        } else {
            Toast.makeText(contex, "do nothiinng itda ann errfor", Toast.LENGTH_SHORT).show()

        }
        if (position == 2) {
            val intent = Intent(contex, BirthdayActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            contex.startActivity(intent)
        } else {
            Toast.makeText(contex, "do nothiinng itda ann errfor", Toast.LENGTH_SHORT).show()

        }
        if (position == 3) {
            val intent = Intent(contex, LockerLoginPage::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            contex.startActivity(intent)
        } else {
            Toast.makeText(contex, "do nothiinng itda ann errfor", Toast.LENGTH_SHORT).show()

        }
    }

    override fun getItemCount(): Int {
        return main_data_list.size
    }
}