package com.dew.newsapplication.utility.neworkutil

import com.dew.newsapplication.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AppInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val req = chain.request()
        return chain.proceed(req.newBuilder().url(
            req.url.newBuilder().addQueryParameter("apiKey", BuildConfig.NEWS_API_KEY).build()
        ).build())
    }
}