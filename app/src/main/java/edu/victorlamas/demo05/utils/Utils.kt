package edu.victorlamas.demo05.utils

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

/**
 * Comprueba si hay conexión a internet.
 * @param context Contexto de la aplicación.
 * @return True si hay conexión, false si no la hay.
 */
fun checkConnection(context: Context): Boolean {
    val cm = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = cm.activeNetwork

    if (networkInfo != null) {
        val activeNetwork = cm.getNetworkCapabilities(networkInfo)
        if (activeNetwork != null)
            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
    }
    return false
}

/**
 * Estados para filtrar las series.
 */
enum class ShowsFilter {
    ALPHABETICAL_ASCENDANT,
    ALPAHABETICAL_DESCENDANT,
    NOTES_ASCENDANT,
    NOTES_DESCENDANT
}

var showsFilter = ShowsFilter.ALPHABETICAL_ASCENDANT