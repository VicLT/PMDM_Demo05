package edu.victorlamas.demo05

import android.app.Application
import androidx.room.Room
import edu.victorlamas.demo05.data.StateShowsDatabase

/**
 * Clase RoomApplication.kt
 * Inicializa la base de datos en local con Room.
 * @autor Víctor Lamas
 */
class RoomApplication : Application() {
    lateinit var stateShowsDB: StateShowsDatabase
    private set

    override fun onCreate() {
        super.onCreate()
        stateShowsDB = Room.databaseBuilder(
            this,
            StateShowsDatabase::class.java,
            "StateShows-db"
        ).build()
    }
}