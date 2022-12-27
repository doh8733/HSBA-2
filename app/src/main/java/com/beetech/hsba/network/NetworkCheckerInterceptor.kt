package com.beetech.hsba.network

import android.content.Context
import com.beetech.hsba.utils.DeviceUtil


import java.io.IOException

import okhttp3.Interceptor
import okhttp3.Response

class NetworkCheckerInterceptor(private val context: Context) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        return if (DeviceUtil.hasConnection(context)) {
            chain.proceed(chain.request().newBuilder()
                .addHeader("Content-Type","application/json")
                .addHeader("version","2.0.0")
                .addHeader("device","1").build()
            )
        } else {
            throw NoConnectivityException()
        }
    }

    inner class NoConnectivityException : Exception() {
        override val message: String?
            get() = "I'm Died!"
    }
}
