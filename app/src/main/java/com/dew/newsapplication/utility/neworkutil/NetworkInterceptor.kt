package com.dew.newsapplication.utility.neworkutil

import com.dew.newsapplication.utility.app.App
import com.dew.newsapplication.utility.isNetworkAvailable
import com.msewa.healthism_merchant.util.NetworkException
import okhttp3.Interceptor
import okhttp3.Response

class NetworkInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if(!isNetworkAvailable(App.getContext())){
            throw NetworkException()
        }else{
            return  chain.proceed(chain.request())
        }
    }
}