package com.beetech.hsba.network

import android.content.Context
import android.util.Log
import com.beetech.hsba.base.adapter.RecyclerViewAdapter.Companion.TAG
import com.beetech.hsba.entity.login.Data
import com.beetech.hsba.extension.getString
import com.beetech.hsba.utils.Define
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class AddHeaderInterceptor(context: Context) : Interceptor {


    private val data = context.getSharedPreferences("DATA", Context.MODE_PRIVATE)
    private var token = data.getString(Define.Database.User.DATA_USER, "").toString()
    private val dataObj = Gson().fromJson(token, Data::class.java)


    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        Log.e(TAG, "intercept: $dataObj", )
        return chain.proceed(
            chain.request()
                .newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("version", "2.0.0")
                .addHeader("device", "1")
                .build()

        )
    }


}