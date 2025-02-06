package edu.victorlamas.demo05.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import edu.victorlamas.demo05.data.ShowsRepository
import edu.victorlamas.demo05.model.StateShows
import edu.victorlamas.demo05.model.shows.Shows
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 * Class MainViewModel.kt
 * Gestiona las operaciones y el estado de los datos en la UI de MainActivity
 * @author Víctor Lamas
 *
 * @param repository Permite recuperar las series y sus estados
 */
class MainViewModel (private val repository: ShowsRepository) : ViewModel() {
    private var _currentShows = repository.fetchShows()
    val currentShows: Flow<Shows>
        get() = _currentShows

    val stateShows: Flow<List<StateShows>> = repository.fetchStateShows()

    /**
     * Actualiza el estado de una serie.
     * @param stateShows Estados de favorito y visto.
     */
    fun updateStateShows(stateShows: StateShows) {
        viewModelScope.launch {
            val stateShowsAux = stateShows.copy(
                stateFavorite = stateShows.stateFavorite,
                stateWatched = stateShows.stateWatched
            )
            repository.saveStateShow(stateShowsAux)
        }
    }
}

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(private val repository: ShowsRepository)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}