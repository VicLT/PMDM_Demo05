package edu.victorlamas.demo05.data

import edu.victorlamas.demo05.model.cast.Cast
import edu.victorlamas.demo05.model.shows.Shows
import edu.victorlamas.demo05.model.shows.ShowsItem
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Class Retrofit2Api.kt
 * Conexión con la API de TVMaze mediante Retrofit2.
 * @author Víctor Lamas
 */
class Retrofit2Api {
    companion object {
        private const val BASE_URL = "https://api.tvmaze.com/"

        fun getRetrofit2Api(): Retrofit2ApiInterface {
            return Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build()
                .create(Retrofit2ApiInterface::class.java)
        }
    }
}

/**
 * Descarga las pelícuas/series y los actores.
 * @author Víctor Lamas
 */
interface Retrofit2ApiInterface {
    @GET("shows")
    suspend fun getShows(): Shows

    @GET("shows/{id}")
    suspend fun getShowById(@Path("id") id: Int): ShowsItem

    @GET("shows/{id}/cast")
    suspend fun getCastByShowId(@Path("id") id: Int): Cast
}