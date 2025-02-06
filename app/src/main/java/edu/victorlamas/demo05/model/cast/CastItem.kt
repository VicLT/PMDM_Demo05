package edu.victorlamas.demo05.model.cast


import com.google.gson.annotations.SerializedName

/**
 * DataClass CastItem.kt
 * Clase que representa los actores de una serie.
 * @author Víctor Lamas
 */
data class CastItem(
    @SerializedName("character")
    val character: Character,
    @SerializedName("person")
    val person: Person,
    @SerializedName("self")
    val self: Boolean,
    @SerializedName("voice")
    val voice: Boolean
)