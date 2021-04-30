package com.twentytwo.notebox.Activities.BdaysActivity

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.twentytwo.notebox.R
import kotlinx.android.synthetic.main.item__birthday.*
import kotlin.collections.ArrayList

class BirthDay_Adapter(private  val  context:Context,private  val birthdayDataClass:ArrayList<Birthday_data_class>):RecyclerView.Adapter<BirthDay_Adapter.ViewHolder>(){
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var Bd_name = itemView.findViewById<TextView>(R.id.bday_name)
        var Bd_date = itemView.findViewById<TextView>(R.id.bday_date)
        var remind_me = itemView.findViewById<Switch>(R.id.bday_switch)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return  ViewHolder(LayoutInflater.from(context).inflate(R.layout.item__birthday,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bdataList : Birthday_data_class = birthdayDataClass.get(position)
        holder.Bd_name.text = bdataList.Bday_name
        holder.Bd_date.text = bdataList.Bday_date
        holder.remind_me.isChecked = bdataList.Switch == true
    }

    override fun getItemCount(): Int {
        return birthdayDataClass.size
    }

}