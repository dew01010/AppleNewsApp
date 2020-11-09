package com.dew.newsapplication.ui.webView

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import com.dew.newsapplication.common.LoadingDialog
import com.dew.newsapplication.databinding.ActivityWebViewBinding

/*
* this activity is use to load  news web sites using web view */
class WebViewActivity : AppCompatActivity() {

    private var url: String? = ""
    private lateinit var loadingDialog: LoadingDialog
    private lateinit var binding: ActivityWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadingDialog=LoadingDialog(this)
        url = intent.getStringExtra(PARAM)
        // view binding
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // it handles to finish this page
        binding.backBt.setOnClickListener {
            onBackPressed()
        }
        loadUrl()
    }

    // setting up web view and initialize url to load in web view
    private fun loadUrl(){
        loadingDialog.show()
        binding.webView.loadUrl(url)
        binding.webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                if (newProgress == 100) {
                    loadingDialog.dismiss()
                }
            }
        }
    }

    companion object{
        /**
         * Returns a new intent with  given param
         */
        const val PARAM="param"
        fun newIntent(activity: Activity, url:String):Intent{
            val i = Intent(activity, WebViewActivity::class.java)
            i.putExtra(PARAM,url)
            return i
        }
    }


}
