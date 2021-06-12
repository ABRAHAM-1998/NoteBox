package com.twentytwo.notebox.Activities.SecurePages

import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.twentytwo.notebox.R

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        var prefData: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        var data = prefData.getBoolean("sync", true)
        
        fun GetAppVersion(context:Context):String{
            var version = ""
            try {
                val pInfo = context.packageManager.getPackageInfo(context.packageName,0)
                version = pInfo.versionName
            }catch (e:PackageManager.NameNotFoundException){
                e.printStackTrace()
            }
            return version
        }
        Toast.makeText(this, "${GetAppVersion(this)}", Toast.LENGTH_SHORT).show()


    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)

        }
    }
}