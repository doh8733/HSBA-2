package com.beetech.hsba.entity.login


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("accessToken")
    var accessToken: String?,
    @SerializedName("address")
    var address: String?,
    @SerializedName("apple_id")
    var appleId: Any?,
    @SerializedName("avatar")
    var avatar: String?,
    @SerializedName("birthday")
    var birthday: String?,
    @SerializedName("check_user")
    var checkUser: Any?,
    @SerializedName("created_at")
    var createdAt: Any?,
    @SerializedName("deleted_at")
    var deletedAt: Any?,
    @SerializedName("email")
    var email: String?,
    @SerializedName("facebook_id")
    var facebookId: Any?,
    @SerializedName("gender")
    var gender: Int?,
    @SerializedName("google_id")
    var googleId: Any?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("login_type")
    var loginType: Int?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("phone")
    var phone: String?,
    @SerializedName("status")
    var status: Int?,
    @SerializedName("typeAccount")
    var typeAccount: Int?,
    @SerializedName("updated_at")
    var updatedAt: String?,
    @SerializedName("username")
    var username: String?,
    @SerializedName("zalo_id")
    var zaloId: Any?
)