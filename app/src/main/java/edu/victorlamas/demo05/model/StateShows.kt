package edu.victorlamas.demo05.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * DataClass StateShows.kt
 * Clase que guarda los estados de favorita y vista de una serie.
 * @author Víctor Lamas
 */
@Entity
data class StateShows(
    @PrimaryKey
    val idShow: Int = 0,
    val stateFavorite: Boolean = false,
    val stateWatched: Boolean = false
)