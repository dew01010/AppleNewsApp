package com.dew.newsapplication.utility.app

import android.app.Application
import android.content.Context

class App:Application() {
    companion object{
        private var context:Context?=null
        fun getContext()=
            context
    }
    override fun onCreate() {
        super.onCreate()
        context =applicationContext
    }

}