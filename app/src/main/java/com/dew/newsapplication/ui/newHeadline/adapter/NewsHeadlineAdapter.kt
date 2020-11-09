package com.dew.newsapplication.ui.newHeadline.adapter

import android.content.Context
import android.net.ParseException
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dew.newsapplication.model.ArticleInfo
import com.dew.newsapplication.databinding.RowArticleBinding
import com.dew.newsapplication.utility.dateFormat
import com.dew.newsapplication.utility.loadImage

/*
* THIS CLASS IS ABSTRACTIONS LAYER BETWEEN VIEW AND DATA WHICH HANDLES TO BIND DATA TO VIEW*/

class NewsHeadlineAdapter(val context: Context, val list: ArrayList<ArticleInfo?>, val callback:NewsHeadlineAdapterCallback) :
    RecyclerView.Adapter<NewsHeadlineAdapter.ViewHolder>() {

    inner class ViewHolder(val viewBinding: RowArticleBinding) : RecyclerView.ViewHolder(viewBinding.root) {
        fun bindViews(articleInfo: ArticleInfo?){
            viewBinding.timeStampTv.text= articleInfo?.publishedAt
            viewBinding.titleTv.text=articleInfo?.title
            viewBinding.img.loadImage(context,articleInfo?.urlToImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= RowArticleBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindViews(list[position])
        holder.viewBinding.root.setOnClickListener {
            callback.onClickRow(list[position]?.url!!)
        }
    }

    // call back to get data from adapter
    interface NewsHeadlineAdapterCallback{
        fun onClickRow(url:String)
    }
}