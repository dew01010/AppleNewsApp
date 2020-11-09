package com.dew.newsapplication.common

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.dew.newsapplication.R
/*
* this is base Fragment that handle all common methods  while calling api that take work with fragment life cycle */
open class BaseFrag:Fragment() {
    private lateinit var loadingDialog: LoadingDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadingDialog= LoadingDialog(requireContext())
    }

    protected fun showLoading(){
        loadingDialog.show()
    }

    protected fun dismissLoading(){
        loadingDialog.dismiss()
    }

    protected fun showMsg(s:String){
        Toast.makeText(requireContext(),s,Toast.LENGTH_LONG).show()
    }

    protected fun noInternet(listener: OnRetryConnectionListener, value: Int ){
        val builder = AlertDialog.Builder(requireContext())
        //set title for alert dialog
        builder.setTitle(R.string.no_internet)
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        //performing positive action
        builder.setPositiveButton("retry"){dialogInterface, which ->
            listener.onConnectionRetry(value)
        }
        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

}