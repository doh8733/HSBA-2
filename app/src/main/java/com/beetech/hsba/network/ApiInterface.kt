package com.beetech.hsba.network


import com.beetech.hsba.base.entity.BaseListLoadMoreResponse
import com.beetech.hsba.base.entity.BaseListResponse
import com.beetech.hsba.base.entity.BaseObjectResponse
import com.beetech.hsba.entity.login.LoginRequest
import com.beetech.hsba.entity.LoginResponse
import com.beetech.hsba.entity.login.User
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
    fun login(@Body loginRequest: LoginRequest) : Single<BaseObjectResponse<LoginResponse>>

    @POST("user/login")
    fun loginApp(@Body loginRequest: LoginRequest): Single<BaseObjectResponse<com.beetech.hsba.entity.login.User>>

    @GET("user/specialty")
    fun getSpecialty() :Single<BaseListResponse<com.beetech.hsba.entity.specialty.Specialty>>
    @GET("user/services")
    fun getService() :Single<BaseListResponse<Services>>

    @GET("user/medical-history?")
    fun getData(
        @Query("page")indexPage:Int?,
        @Header("Authorization")token:String
    ):Single<BaseListLoadMoreResponse<com.beetech.hsba.entity.medical_history.MedicalHistory>>
}
