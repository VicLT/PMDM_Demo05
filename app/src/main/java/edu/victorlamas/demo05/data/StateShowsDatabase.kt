package edu.victorlamas.demo05.data

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RoomDatabase
import edu.victorlamas.demo05.model.StateShows
import kotlinx.coroutines.flow.Flow

/**
 * Class StateShowsDatabase.kt
 * Define una base de datos con la tabla StateShows y su DAO asociado.
 * @author Víctor Lamas
 */
@Database(entities = [StateShows::class], version = 1)
abstract class StateShowsDatabase : RoomDatabase() {
    abstract fun stateShowsDao(): StateShowsDao
}

/**
 * Implementaciones del DAO para las clases hijas.
 * @author Víctor Lamas
 */
@Dao
interface StateShowsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStateShow(stateShows: StateShows)

    @Query("SELECT * FROM StateShows")
    fun getAllStateShows(): Flow<List<StateShows>>

    @Query("SELECT * FROM StateShows WHERE idShow = :idShow")
    suspend fun getStateShowsByIdShow(idShow: Int): StateShows?
}