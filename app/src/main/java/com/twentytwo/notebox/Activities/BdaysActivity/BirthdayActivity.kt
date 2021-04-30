package com.twentytwo.notebox.Activities.BdaysActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.twentytwo.notebox.Activities.DashBoard.Main_Adapter_cycle
import com.twentytwo.notebox.Activities.DashBoard.recycler_mainDataClass
import com.twentytwo.notebox.R
import kotlinx.android.synthetic.main.activity_birthday.*

class BirthdayActivity : AppCompatActivity() {

    private var charItem: ArrayList<recycler_mainDataClass>? = null
    private var gridLayoutManager: GridLayoutManager? = null
    private var alphaAdapters: Main_Adapter_cycle? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_birthday)

        ///////////////////////


        floatingActionButton.setOnClickListener{
            startActivity(Intent(this,CreateBirthdayActivity::class.java))
        }

        val Birthday_recyiew = findViewById<RecyclerView>(R.id.birthday_recyclerView)
        Birthday_recyiew.layoutManager = LinearLayoutManager(this)

        var BdayLIst : ArrayList<Birthday_data_class> = ArrayList()
        Birthday_recyiew.adapter = BirthDay_Adapter(applicationContext,BdayLIst)

        BdayLIst.add(Birthday_data_class("kutuu","12-34-4344",true))
        BdayLIst.add(Birthday_data_class("kutuu","12-34-4344",true))
        BdayLIst.add(Birthday_data_class("kutuu","12-34-4344",true))
        BdayLIst.add(Birthday_data_class("kutuu","12-34-4344",true))
        BdayLIst.add(Birthday_data_class("kutuu","12-34-4344",true))
        BdayLIst.add(Birthday_data_class("kutuu","12-34-4344",true))
        BdayLIst.add(Birthday_data_class("kutuu","12-34-4344",true))
        BdayLIst.add(Birthday_data_class("kutuu","12-34-4344",true))
        BdayLIst.add(Birthday_data_class("kutuu","12-34-4344",true))
        BdayLIst.add(Birthday_data_class("kutuu","12-34-4344",true))
        BdayLIst.add(Birthday_data_class("kutuu","12-34-4344",true))
        BdayLIst.add(Birthday_data_class("kutuu","12-34-4344",true))
        BdayLIst.add(Birthday_data_class("kutuu","12-34-4344",true))
        BdayLIst.add(Birthday_data_class("kutuu","12-34-4344",true))

    }
}