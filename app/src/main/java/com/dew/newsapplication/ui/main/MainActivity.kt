package com.dew.newsapplication.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.MediaController
import androidx.appcompat.app.AppCompatActivity
import com.dew.newsapplication.databinding.ActivityMainBinding
import com.dew.newsapplication.utility.pref.AppPref

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // view binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}