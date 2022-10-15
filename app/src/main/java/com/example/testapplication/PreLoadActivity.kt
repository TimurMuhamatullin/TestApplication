package com.example.testapplication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.telephony.TelephonyManager
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig

class PreLoadActivity: AppCompatActivity() {
    private lateinit var mSettings: SharedPreferences
    private lateinit var path: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)
        intent = Intent(this, MainActivity::class.java)
        mSettings = getSharedPreferences("setting", Context.MODE_PRIVATE)
        path = mSettings.getString("key", null).toString()
        if(path == null.toString()){
            loadFire()
        } else sendData(true, path)

    }
    private fun sendData(b: Boolean, link:String){
        if (b) intent.putExtra("param", "0")
        else   intent.putExtra("param", "1")
        intent.putExtra("link", link)
        startActivity(intent)
        finish()
    }

    private fun loadFire(){
        val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
        val getUrl = remoteConfig.getString("url")
        remoteConfig.fetchAndActivate().addOnCompleteListener {
            if( getUrl.isEmpty() || isSimDoesntActivated() || Build.MANUFACTURER.contains("google") || Build.MANUFACTURER.contains("Google") ){
                val filePlace = "android.resource://"+ packageName + "/raw/" + R.raw.video
                sendData(false, filePlace)
            }else{
                val editor: SharedPreferences.Editor = mSettings.edit()
                editor.putString("key", getUrl)
                editor.apply()
                sendData(true, getUrl)
            }
        }
    }

    private fun isSimDoesntActivated (): Boolean {
        val tm: TelephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return tm.simState == TelephonyManager.SIM_STATE_ABSENT
    }
}