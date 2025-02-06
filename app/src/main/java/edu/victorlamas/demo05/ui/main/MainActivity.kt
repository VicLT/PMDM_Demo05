package edu.victorlamas.demo05.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import edu.victorlamas.demo05.R
import edu.victorlamas.demo05.RoomApplication
import edu.victorlamas.demo05.data.ShowsRepository
import edu.victorlamas.demo05.data.TVMazeDataSource
import edu.victorlamas.demo05.databinding.ActivityMainBinding
import edu.victorlamas.demo05.model.StateShows
import edu.victorlamas.demo05.ui.detail.DetailShowActivity
import edu.victorlamas.demo05.utils.ShowsFilter
import edu.victorlamas.demo05.utils.checkConnection
import edu.victorlamas.demo05.utils.showsFilter
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

/**
 * Class MainActivity.kt
 * Muestra una lista de series que pueden marcarse como favoritas y/o vistas.
 * @author Víctor Lamas
 */
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val vm: MainViewModel by viewModels {
        val db = (application as RoomApplication).stateShowsDB
        val dataSource = TVMazeDataSource(db.stateShowsDao())
        val repository = ShowsRepository(dataSource)
        MainViewModelFactory(repository)
    }

    private val adapter = ShowsAdapter(
        onClickShowsItem = { idShow ->
            DetailShowActivity.navigate(this@MainActivity, idShow)
        },
        onClickShowFavorite = { show ->
            vm.updateStateShows(
                StateShows(
                    idShow = show.id,
                    stateFavorite = !show.favorite,
                    stateWatched = show.watched
                )
            )
        },
        onClickShowWatch = { show ->
            vm.updateStateShows(
                StateShows(
                    idShow = show.id,
                    stateFavorite = show.favorite,
                    stateWatched = !show.watched
                )
            )
        }
    )

    /**
     * Mantiene actualizada la lista de series.
     * @author Víctor Lamas
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars =
                insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )
            insets
        }

        binding.recyclerView.adapter = adapter

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                getShows()
            }
        }
    }

    /**
     * Configura el menú de la toolbar para usar filtros de ordenación.
     * @author Víctor Lamas
     */
    override fun onStart() {
        super.onStart()

        binding.swipeRefresh.setOnRefreshListener {
            lifecycleScope.launch {
                getShows()
            }
        }

        binding.mToolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.opt_sort_by_name -> {
                    showsFilter = if (showsFilter == ShowsFilter.ALPHABETICAL_ASCENDANT) {
                        ShowsFilter.ALPAHABETICAL_DESCENDANT
                    } else {
                        ShowsFilter.ALPHABETICAL_ASCENDANT
                    }
                    lifecycleScope.launch {
                        getShows()
                    }
                    true
                }

                R.id.opt_sort_by_rating -> {
                    showsFilter = if (showsFilter == ShowsFilter.NOTES_ASCENDANT) {
                        ShowsFilter.NOTES_DESCENDANT
                    } else {
                        ShowsFilter.NOTES_ASCENDANT
                    }
                    lifecycleScope.launch {
                        getShows()
                    }
                    true
                }

                else -> false
            }
        }
    }

    /**
     * Obtiene las series y las muestra en el RecyclerView.
     * @author Víctor Lamas
     */
    private suspend fun getShows() {
        adapter.submitList(emptyList())
        if (checkConnection(this)) {
            binding.swipeRefresh.isRefreshing = true
            combine(vm.currentShows, vm.stateShows) { shows, stateShows ->
                shows.forEach { element ->
                    val stateShow = stateShows.find { stateShow ->
                        stateShow.idShow == element.id
                    }
                    element.favorite = stateShow?.stateFavorite ?: false
                    element.watched = stateShow?.stateWatched ?: false
                }

                when (showsFilter) {
                    ShowsFilter.ALPHABETICAL_ASCENDANT -> shows.sortedBy {
                            showItem -> showItem.name.uppercase()
                    }
                    ShowsFilter.ALPAHABETICAL_DESCENDANT -> shows.sortedByDescending {
                            showItem -> showItem.name.uppercase()
                    }
                    ShowsFilter.NOTES_ASCENDANT -> shows.sortedBy {
                            showItem -> showItem.rating.average
                    }
                    ShowsFilter.NOTES_DESCENDANT -> shows.sortedByDescending {
                            showItem -> showItem.rating.average
                    }
                }
            }.catch {
                binding.swipeRefresh.isRefreshing = false
                Toast.makeText(
                    this@MainActivity, it.message,
                    Toast.LENGTH_SHORT
                ).show()
            }.collect { shows ->
                adapter.submitList(shows)
                binding.swipeRefresh.isRefreshing = false
            }
        } else {
            binding.swipeRefresh.isRefreshing = false
            Toast.makeText(
                this@MainActivity,
                getString(R.string.txt_noConnection),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}