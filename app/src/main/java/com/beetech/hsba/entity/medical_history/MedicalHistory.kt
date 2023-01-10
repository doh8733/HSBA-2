package com.beetech.hsba.entity.medical_history


import com.google.gson.annotations.SerializedName

data class MedicalHistory(
    @SerializedName("avatar")
    var avatar: String?,
    @SerializedName("created_by")
    var createdBy: Int?,
    @SerializedName("date")
    var date: String?,
    @SerializedName("date_medical_histories")
    var dateMedicalHistories: String?,
    @SerializedName("doctor_name")
    var doctorName: String?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("position")
    var position: String?,
    @SerializedName("service_specialty_icon")
    var serviceSpecialtyIcon: String?,
    @SerializedName("service_specialty_name")
    var serviceSpecialtyName: String?
)