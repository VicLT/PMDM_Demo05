package edu.victorlamas.demo05.model.shows


import com.google.gson.annotations.SerializedName

data class DvdCountry(
    @SerializedName("code")
    val code: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("timezone")
    val timezone: String
)