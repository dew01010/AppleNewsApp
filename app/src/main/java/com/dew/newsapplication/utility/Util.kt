package com.dew.newsapplication.utility

import android.content.Context
import android.icu.util.ULocale.getCountry
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.ParseException
import android.os.Build
import android.util.Log
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*


/*
* this is tools class contains common methods, so we can use in every where */

fun dateFormat(oldDate: String?): String? {
    val newDate: String?
    val dateFormat = SimpleDateFormat("E, d MMM yyyy", Locale.getDefault())
    newDate = try {
        val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZ").parse(oldDate)
        dateFormat.format(date)
    } catch (e: ParseException) {
        e.printStackTrace()
        oldDate
    }
    return newDate
}


fun isNetworkAvailable(context: Context?): Boolean {
    if (context == null) return false
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (connectivityManager != null) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    return true
                }
            }
        } else {
            try {
                val activeNetworkInfo =
                    connectivityManager.activeNetworkInfo
                if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                    Log.i("update_statut", "Network is available : true")
                    return true
                }
            } catch (e: Exception) {
                Log.i("update_statut", "" + e.message)
            }
        }
    }
    Log.i("update_statut", "Network is available : FALSE ")
    return false
}

fun getLoader(context: Context): CircularProgressDrawable {
    val circularProgressDrawable = CircularProgressDrawable(context)
    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.start()
    return circularProgressDrawable
}
