package com.digitalhouse.moviewallet.network

import com.digitalhouse.moviewallet.model.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ServiceMovieDB {

    @GET("configuration")
    suspend fun getMoviesConfiguration(): MovieConfiguration

    @GET("genre/movie/list")
    suspend fun getGenre(
        @Query("language") language: String?
    ): ListGenre

    @GET("movie/now_playing")
    suspend fun getReleaseMovie(
        @Query("language") language: String?,
        @Query("region") region: String?
    ): UpComing

    @GET("discover/movie")
    suspend fun getMoviesByGenre(
        @Query("language") language: String?,
        @Query("with_genres") genre: String?,
        @Query("page") page: Int?,
        @Query("watch_region") region: String,
        @Query("watch_monetization_types") watchMonetization: String
    ): DiscoverMovies

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: String,
        @Query("language") language: String?
    ): MovieDetail

    @GET("movie/{movie_id}/credits")
    suspend fun getCreditMovie(
        @Path("movie_id") movieId: String,
        @Query("language") language: String?
    ): CreditMovie

    @GET("movie/popular")
    suspend fun getPopularMovie(
        @Query("language") language: String?,
        @Query("region") region: String?
    ): PopularMovie

    @GET("movie/{movie_id}/watch/providers")
    suspend fun getProviderMovie(
        @Path("movie_id") movieId: String
    ): ProviderResponse

    @GET("movie/{movie_id}/images")
    suspend fun getImageMovie(
        @Path("movie_id") movieId: String,
        @Query("include_image_language")pt:String
    ):ImageMovieResponse
}