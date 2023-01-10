package com.beetech.hsba.entity.login

import com.google.gson.annotations.SerializedName

class LoginRequest {
    @SerializedName("username")
    var username:String? = null
    @SerializedName("password")
    var password:String? = null


}