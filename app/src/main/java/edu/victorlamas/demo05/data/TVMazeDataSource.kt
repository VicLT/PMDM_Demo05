package edu.victorlamas.demo05.data

import edu.victorlamas.demo05.model.StateShows
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Class TVMazeDataSource.kt
 * Fuente de datos que provienen de TVMazeApi y su DAO.
 * @author Víctor Lamas
 *
 * @param db Datos heredados de la StateShowsDatabase.
 */
class TVMazeDataSource(private val db: StateShowsDao) {
    private val api = Retrofit2Api.getRetrofit2Api()

    fun getShows() = flow {
        emit(api.getShows())
    }

    fun getShowById(id: Int) = flow {
        emit(api.getShowById(id))
    }

    fun getCastByShowId(id: Int) = flow {
        emit(api.getCastByShowId(id))
    }

    suspend fun insertStateShow(stateShow: StateShows) {
        db.insertStateShow(stateShow)
    }

    fun getAllStateShows(): Flow<List<StateShows>> {
        return db.getAllStateShows()
    }

    fun getStateShowByIdShow(id: Int) = flow {
        emit(db.getStateShowsByIdShow(id))
    }
}