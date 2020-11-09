package com.dew.newsapplication.network.apiservice

import android.util.Log
import com.dew.newsapplication.model.HeadLineRes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

object ApiRequest {
    fun getTopHeadlinesBySource(
        sourceId: String,
        page:Int,
        listener: (res: HeadLineRes?, resultCode: String?, e: Exception?) -> Unit
    ) {
        val apiInterface = ApiFactory.client.create(
            ApiInterface::class.java)
        val call = apiInterface.getTopHeadLinesByCountry(sourceId,page)
        call.enqueue(object : Callback<HeadLineRes> {
            override fun onFailure(call: Call<HeadLineRes>, t: Throwable) {
                Log.v("ERROR: ", t.message!!)
                listener(null,
                    ApiConstant.CODE_FAILED, t as Exception)
            }
            override fun onResponse(call: Call<HeadLineRes>, response: Response<HeadLineRes>) {
                Log.v("RES: ", response.body().toString())
                if (response.isSuccessful) {
                    listener(
                        response.body(),
                        ApiConstant.CODE_SUCCESS,
                        null
                    )
                } else {
                    listener(null,
                        ApiConstant.CODE_FAILED, Exception("something went wrong !!"))
                }
            }
        })

    }
}