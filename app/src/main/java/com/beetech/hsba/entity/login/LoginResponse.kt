package com.beetech.hsba.entity.login

data class LoginResponse(
    val code: Int,
    val `data`: Data,
    val msg: String
)