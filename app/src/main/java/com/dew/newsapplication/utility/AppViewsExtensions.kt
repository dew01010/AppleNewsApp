package com.dew.newsapplication.utility

import android.content.Context
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide

/*
* this class contains all extensions of views so we can use in every where */

fun <T> AppCompatImageView.loadImage(context: Context, path:T, isCircle:Boolean=false){
    if(isCircle)
        Glide.with(this).load(path).circleCrop().placeholder(getLoader(context)).into(this)
    else
        Glide.with(this).load(path).placeholder(getLoader(context)).into(this)
}
