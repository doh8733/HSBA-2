package com.beetech.hsba.network

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class AddHeaderInterceptor(context: Context) :Interceptor{

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            chain.request()
            .newBuilder()
            .addHeader("Content-Type","application/json")
            .addHeader("version","2.0.0")
            .addHeader("device","1").build()
        )
    }


}