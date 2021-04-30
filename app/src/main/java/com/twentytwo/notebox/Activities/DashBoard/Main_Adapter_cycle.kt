package com.twentytwo.notebox.Activities.DashBoard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
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
        return ViewHolder(LayoutInflater.from(contex).inflate(R.layout.main_recycler_item,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val maindata : recycler_mainDataClass = main_data_list.get(position)
        holder.titles.text = maindata.title_item
        holder.main_rec_img.setImageResource(maindata.icons!!)
        holder.titles.textSize = maindata.size.toFloat()
    }

    override fun getItemCount(): Int {
        return  main_data_list.size
    }
}