package com.beetech.hsba.network

import com.beetech.hsba.base.entity.BaseListLoadMoreResponse
import com.beetech.hsba.base.entity.BaseListResponse
import com.beetech.hsba.base.entity.BaseObjectResponse
import com.beetech.hsba.entity.LoginRequest
import com.beetech.hsba.entity.User
import com.beetech.hsba.entity.login.Data
import com.beetech.hsba.entity.services.Services
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class Repository @Inject constructor(val apiInterface: ApiInterface) {
    fun getData(pageIndex: Int): Single<BaseListLoadMoreResponse<User>> {
        return apiInterface.getDataUser("f", pageIndex)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun login(username: String, password: String): Single<BaseObjectResponse<Data>> {
        val loginRequest = LoginRequest()
        loginRequest.username = username
        loginRequest.password = password

        return apiInterface.loginApp(loginRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getSpecialty(): Single<BaseListResponse<com.beetech.hsba.entity.specialty.Specialty>> {
        return apiInterface.getSpecialty()
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    fun getServices(): Single<BaseListResponse<Services>> {
        return apiInterface.getService()
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }
}