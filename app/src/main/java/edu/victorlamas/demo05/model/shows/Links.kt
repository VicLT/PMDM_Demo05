package edu.victorlamas.demo05.model.shows


import com.google.gson.annotations.SerializedName

data class Links(
    @SerializedName("nextepisode")
    val nextepisode: Nextepisode,
    @SerializedName("previousepisode")
    val previousepisode: Previousepisode,
    @SerializedName("self")
    val self: Self
)