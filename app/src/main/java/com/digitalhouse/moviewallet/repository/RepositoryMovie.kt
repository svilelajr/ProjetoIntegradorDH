package com.digitalhouse.moviewallet.repository

import com.digitalhouse.moviewallet.network.RetrofitInit
import com.digitalhouse.moviewallet.network.ServiceMovieDB

class RepositoryMovie {

    companion object {
        const val chave = "c4640d12c2eafd90534ca10ba1b9ddf1"
    }

    private var url = "https://api.themoviedb.org/3/"
    private var service = ServiceMovieDB::class
    private val language = "pt-BR"
    private val region = "BR"

    private val serviceMovie = RetrofitInit(url).create(service)

    suspend fun getConfiguration() = serviceMovie.getMoviesConfiguration()
    suspend fun getGenre() = serviceMovie.getGenre(language)
    suspend fun getReleaseMovie() = serviceMovie.getReleaseMovie(language,region)
    suspend fun getMoviesByGenre(genre: String?, page: Int = 1) = serviceMovie.getMoviesByGenre(language, genre, page,region,"flatrate")
    suspend fun getMovieDetails(movieID: String) = serviceMovie.getMovieDetail(movieID, language)
    suspend fun getCreditMovie(movieID: String) = serviceMovie.getCreditMovie(movieID,language)
    suspend fun getPopularMovie() = serviceMovie.getPopularMovie(language,region)
    suspend fun getProviderMovie(movieID: String)= serviceMovie.getProviderMovie(movieID)
    suspend fun getImageMovie(movieID: String) = serviceMovie.getImageMovie(movieID,"pt")
}