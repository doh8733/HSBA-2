package com.beetech.hsba.entity.services


import com.google.gson.annotations.SerializedName

data class Services(
    @SerializedName("description")
    var description: String?,
    @SerializedName("icon")
    var icon: String?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("image_web")
    var imageWeb: String?,
    @SerializedName("name")
    var name: String?
)