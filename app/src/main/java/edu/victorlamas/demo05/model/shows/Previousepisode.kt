package edu.victorlamas.demo05.model.shows


import com.google.gson.annotations.SerializedName

data class Previousepisode(
    @SerializedName("href")
    val href: String,
    @SerializedName("name")
    val name: String
)