package edu.victorlamas.demo05.data

import edu.victorlamas.demo05.model.StateShows
import edu.victorlamas.demo05.model.cast.Cast
import edu.victorlamas.demo05.model.shows.Shows
import edu.victorlamas.demo05.model.shows.ShowsItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking

/**
 * Class ShowsRepository.kt
 * Repositorio que actúa como intermediario entre los VM y ShowsDataSource.
 * @author Víctor Lamas
 *
 * @param dataSource Datos heredados de la clase TVMazeDataSource.
 */
class ShowsRepository(private val dataSource: TVMazeDataSource) {
    fun fetchShows(): Flow<Shows> {
        return dataSource.getShows()
    }

    fun fetchShowById(id: Int): Flow<ShowsItem> {
        return dataSource.getShowById(id)
    }

    fun fetchCastByShowId(id: Int): Flow<Cast> {
        return dataSource.getCastByShowId(id)
    }

    /**
     * Guarda el estado de una serie y bloquea el hilo hasta que termine.
     * @author Víctor Lamas
     *
     * @param stateShow Estados de favorito y visto.
     */
    fun saveStateShow(stateShow: StateShows) = runBlocking {
        dataSource.insertStateShow(stateShow)
        delay(10)
    }

    fun fetchStateShows(): Flow<List<StateShows>> {
        return dataSource.getAllStateShows()
    }

    fun getStateShowByIdShow(idShow: Int): Flow<StateShows?> {
        return dataSource.getStateShowByIdShow(idShow)
    }
}