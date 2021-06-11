package com.digitalhouse.moviewallet.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.digitalhouse.moviewallet.model.Genre
import com.digitalhouse.moviewallet.model.Movie
import com.digitalhouse.moviewallet.repository.RepositoryMovie
import com.digitalhouse.moviewallet.repository.SingletonConfiguration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val repository = RepositoryMovie()
    val listGenre: MutableLiveData<List<Genre>> = MutableLiveData()
    val listReleaseMovie = MutableLiveData<List<Movie>>()
    val listPopularMovie = MutableLiveData<List<Movie>>()
    private var genreApi = mutableListOf<Genre>()
    private val genreHome = mutableListOf(28, 16, 18, 35, 14, 27, 10749)
    val progress by lazy { MutableLiveData<Boolean>() }

    init {
        getConfiguration()
    }

    private fun getConfiguration() = CoroutineScope(Dispatchers.IO).launch {
        try {
            repository.getConfiguration().let { configuration ->
                SingletonConfiguration.setConfiguration(configuration)
                getGenre()
                getReleaseMovies()
                getPopularMovie()
            }
        } catch (error: Throwable) {
            Log.e("Error", "Problema de Configuration $error")
        }
    }

    private fun getGenre() = CoroutineScope(Dispatchers.IO).launch {
        try {
            progress.postValue(true)
            genreHome.forEach { genreH ->
                repository.getGenre().let {
                    it.genres?.forEach { genre ->
                        if (genre.id == genreH) {
                            genreApi.add(genre)
                        }
                    }
                }
            }
            genreApi.forEach { genre ->
                repository.getMoviesByGenre(genre.id.toString(), 1).let { dMovie ->
                    dMovie.movies?.forEach { movie ->
                        val m = movie.genreIds?.get(0)
                        if (m == genre.id) {
                            genre.movies = dMovie.movies as MutableList<Movie>?
                        }
                    }
                }
            }
            listGenre.postValue(genreApi)
        } catch (error: Throwable) {
            Log.e("Error", "Problema de Genre $error")
        } finally {
            progress.postValue(false)
        }
    }

    private fun getReleaseMovies() = CoroutineScope(Dispatchers.IO).launch {
        try {
            repository.getReleaseMovie().let { movie ->
                listReleaseMovie.postValue(movie.releaseMovies)
            }
        } catch (error: Throwable) {
            Log.e("Error", "Problema de Release $error")
        }
    }

    private fun getPopularMovie() = CoroutineScope(Dispatchers.IO).launch {
        try {
            progress.postValue(true)
            repository.getPopularMovie().let { movieP ->
                listPopularMovie.postValue(movieP.movie)
            }
        } catch (error: Throwable) {
            Log.e("Error", "Problema de Popular $error")
        } finally {
            progress.postValue(false)
        }
    }

}