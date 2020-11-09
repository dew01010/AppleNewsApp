package com.dew.newsapplication.common

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.TextView
import com.dew.newsapplication.R

class LoadingDialog(context: Context,private  val message: String?=null) :Dialog(context,R.style.customProgressDialogStyle) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.custom_progress_bar)
        val textView = findViewById<View>(R.id.msg) as TextView
        textView.visibility=View.GONE
        if (!this.message.isNullOrEmpty()) {
            textView.text= this.message
            textView.visibility=View.VISIBLE
        }
        setCancelable(false)
    }
}