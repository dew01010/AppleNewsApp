package com.dew.newsapplication.network.apiservice

import com.dew.newsapplication.model.HeadLineRes
import com.dew.newsapplication.utility.PAGE_LIMIT
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("${ApiUrl.EVERYTHING}?&pageSize=${PAGE_LIMIT}")
    fun getTopHeadLinesByCountry(@Query("q") source:String,@Query("page") page:Int): Call<HeadLineRes>
}