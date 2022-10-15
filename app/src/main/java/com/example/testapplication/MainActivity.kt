package com.example.testapplication

import android.os.Bundle
import android.view.KeyEvent
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.testapplication.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var webVW: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        webVW = findViewById(R.id.webVW)
        val link = intent.extras!!["link"].toString()
        val b = intent.extras!!["param"].toString()

        if (b == "0"){
            openWebClient(link)
            } else if (b == "1"){
                supportFragmentManager.beginTransaction().replace(R.id.FL, PhrasesFragment.newInstance(link)).commit()
            }
    }



    private fun openWebClient(url: String){
        webVW.webViewClient = WebViewClient()
        webVW.apply {
            loadUrl(url)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if ((keyCode == KeyEvent.KEYCODE_BACK) && webVW.canGoBack()) {
            webVW.goBack()
            true
        }else{
            false
        }
    }

}