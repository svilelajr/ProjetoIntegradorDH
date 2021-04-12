package com.digitalhouse.moviewallet.repository

import com.digitalhouse.moviewallet.network.RetrofitInit
import com.digitalhouse.moviewallet.network.ServiceMovieDB

class RepositoryMovie {

    companion object{
        const val chave = "c4640d12c2eafd90534ca10ba1b9ddf1"
    }

    private var url = "https://api.themoviedb.org/3/"
    private var service = ServiceMovieDB::class
    val language = "pt-BR"

    private val serviceMovie = RetrofitInit(url).create(service)

    suspend fun getConfiguration() = serviceMovie.getMoviesConfiguration()
    suspend fun getGenre() = serviceMovie.getGenre(language)
    suspend fun getReleaseMovie() = serviceMovie.getReleaseMovie()
}