package com.dew.newsapplication.api.repo

import com.dew.newsapplication.model.HeadLineRes
import com.dew.newsapplication.network.apiservice.ApiConstant
import com.dew.newsapplication.network.apiservice.ApiRequest
import com.dew.newsapplication.utility.neworkutil.ApiResult

class NewsRepo {
    private var api:ApiRequest = ApiRequest

    fun fetchNewsHeadlines(id:String,page:Int, listener: (result: ApiResult<HeadLineRes>) -> Unit){
        api.getTopHeadlinesBySource(id,page) { res: HeadLineRes?, resultCode: String?, e: Exception? ->
            when (resultCode) {
                ApiConstant.CODE_SUCCESS -> {
                    try {
                        listener(ApiResult.Success(res!!))
                    } catch (e: Exception) {
                        listener(ApiResult.Error(e))
                    }
                }
                ApiConstant.CODE_FAILED -> {
                    listener(ApiResult.Error(e!!))
                }
            }
        }
    }
}