package edu.victorlamas.demo05.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import edu.victorlamas.demo05.data.ShowsRepository
import edu.victorlamas.demo05.model.StateShows
import edu.victorlamas.demo05.model.cast.Cast
import edu.victorlamas.demo05.model.shows.ShowsItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 * Class DetalShowViewModel.kt
 * Gestiona los estados de una serie.
 * @author Víctor Lamas
 *
 * @param repository Recupera los shows.
 * @param idShow ID de la serie.
 */
class DetailShowViewModel(
    private val repository: ShowsRepository,
    idShow: Int
) : ViewModel() {
    val show: Flow<ShowsItem> = repository.fetchShowById(idShow)
    val cast: Flow<Cast> = repository.fetchCastByShowId(idShow)

    val stateShow = repository.getStateShowByIdShow(idShow)

    fun updateStateShow(stateShows: StateShows) {
        viewModelScope.launch {
            val stateShowsCopy = stateShows.copy(
                stateFavorite = stateShows.stateFavorite,
                stateWatched = stateShows.stateWatched
            )
            repository.saveStateShow(stateShowsCopy)
        }
    }
}

/**
 * Class DetalShowViewModel.kt
 * Crea instancias de DetailShowViewModel
 * @author Víctor Lamas
 *
 * @param repository Parámetros necesarios para crear la instancia.
 * @param idShow ID de la nota a gestionar.
 * @return Instancia de DetailShowViewModel personalizada.
 */
@Suppress("UNCHECKED_CAST")
class DetailShowViewModelFactory(
    private val repository: ShowsRepository,
    private val idShow: Int
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailShowViewModel(repository, idShow) as T
    }
}