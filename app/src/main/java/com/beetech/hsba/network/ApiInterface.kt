package com.beetech.hsba.network


import com.beetech.hsba.base.entity.BaseListLoadMoreResponse
import com.beetech.hsba.base.entity.BaseListResponse
import com.beetech.hsba.base.entity.BaseObjectResponse
import com.beetech.hsba.entity.LoginRequest
import com.beetech.hsba.entity.LoginResponse
import com.beetech.hsba.entity.User
import com.beetech.hsba.entity.login.Data
import com.beetech.hsba.entity.services.Services
import io.reactivex.Single
import retrofit2.http.*

interface ApiInterface {

    @GET("search1")
    @Headers("Content-Type: application/json", "lang: vi")
    fun getDataUser(
        @Query("s") keyWord: String,
        @Query("page") page: Int
    ): Single<BaseListLoadMoreResponse<User>>

    @POST("/api/auth/login/customer")
    @Headers("Content-Type: application/json")
    fun login(@Body loginRequest:LoginRequest) : Single<BaseObjectResponse<LoginResponse>>

    @POST("user/login")
    fun loginApp(@Body loginRequest: LoginRequest): Single<BaseObjectResponse<Data>>

    @GET("user/specialty")
    fun getSpecialty() :Single<BaseListResponse<com.beetech.hsba.entity.specialty.Specialty>>
    @GET("user/services")
    fun getService() :Single<BaseListResponse<Services>>
}
