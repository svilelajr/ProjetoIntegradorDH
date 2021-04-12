package com.digitalhouse.moviewallet.network

import com.digitalhouse.moviewallet.model.Genre
import com.digitalhouse.moviewallet.model.ListGenre
import com.digitalhouse.moviewallet.model.MovieConfiguration
import com.digitalhouse.moviewallet.model.ReleaseMovie
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceMovieDB {

    @GET("configuration")
    suspend fun getMoviesConfiguration() : MovieConfiguration

    @GET("genre/movie/list")
    suspend fun getGenre(
        @Query("language") language: String?
    ): ListGenre

    @GET("movie/now_playing")
    suspend fun getReleaseMovie(): ReleaseMovie

}