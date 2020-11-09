package com.dew.newsapplication.utility.pref

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

object AppPref{
    private val PREF= "newsAppPref"
    private val HELP_KEY= "helpPref"
    private val HELP_VIDEO_KEY= "helpVideoPref"

    private fun getSharePref(context: Context):SharedPreferences{
        return context.getSharedPreferences(PREF,Context.MODE_PRIVATE)
    }

    fun isHelpVideoChecked(context: Context):Boolean{
        return getSharePref(context).getBoolean(HELP_KEY,false)
    }
    fun setHelpVideoChecked(context: Context,value:Boolean){
         getSharePref(context).edit().putBoolean(HELP_KEY,value).apply()
    }

    fun getHelpVideoPosition(context: Context):Int{
        return getSharePref(context).getInt(HELP_VIDEO_KEY,0)
    }
    fun setHelpVideoPosition(context: Context,value:Int){
        getSharePref(context).edit().putInt(HELP_VIDEO_KEY,value).apply()
    }
}