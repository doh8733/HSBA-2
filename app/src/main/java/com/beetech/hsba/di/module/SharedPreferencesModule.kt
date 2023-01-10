package com.beetech.hsba.di.module

import android.content.Context
import com.beetech.hsba.entity.login.LoginRequest
import com.beetech.hsba.entity.login.User
import com.beetech.hsba.utils.Define
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPreferencesModule @Inject constructor(@ApplicationContext context: Context) {
    private val prefs = context.getSharedPreferences(Define.KeyPrefs.DATA, Context.MODE_PRIVATE)

    @Singleton
    fun getPrefsSessionTag(): LoginRequest? {
        val data = prefs.getString(Define.KeyPrefs.DATA_SESSION, "").toString()
        return Gson().fromJson(data, LoginRequest::class.java)
    }

    @Singleton
    fun pushPrefsSessionTag(loginRequest: LoginRequest?) {
        prefs.edit().putString(Define.KeyPrefs.DATA_SESSION, Gson().toJson(loginRequest)).apply()

    }

    @Singleton
    fun getPrefsDataUserTag(): User? {
        val data = prefs.getString(Define.KeyPrefs.DATA_USER, "").toString()
        return Gson().fromJson(data, User::class.java)
    }

    @Singleton
    fun pushPrefsDataTag(user: User) {
        prefs.edit().putString(Define.KeyPrefs.DATA_USER, Gson().toJson(user)).apply()

    }
}