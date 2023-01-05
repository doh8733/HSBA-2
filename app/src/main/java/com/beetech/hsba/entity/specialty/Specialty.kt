package com.beetech.hsba.entity.specialty


import com.google.gson.annotations.SerializedName

data class Specialty(
    @SerializedName("description")
    var description: String?,
    @SerializedName("icon")
    var icon: String?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("name")
    var name: String?
)