package com.digitalhouse.moviewallet.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.digitalhouse.moviewallet.model.Genre
import com.digitalhouse.moviewallet.model.Movie
import com.digitalhouse.moviewallet.model.MovieRecycler
import com.digitalhouse.moviewallet.repository.RepositoryMovie
import com.digitalhouse.moviewallet.repository.SingletonConfiguration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val repository = RepositoryMovie()
    val _listGenre = MutableLiveData<List<Genre>>()
    val listGenre: MutableLiveData<List<Genre>> = MutableLiveData()
    val listReleaseMovie = MutableLiveData<List<Movie>>()
    val listPopularMovie = MutableLiveData<List<Movie>>()
    var genreApi = mutableListOf<Genre>()
    val genreHome = mutableListOf(28, 16, 18, 35, 14, 27, 10749)
    val progress by lazy { MutableLiveData<Boolean>() }

    init {
        getConfiguration()
    }

    fun getConfiguration() = CoroutineScope(Dispatchers.IO).launch {
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

    fun getGenre() = CoroutineScope(Dispatchers.IO).launch {
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
            genreApi.forEach { genreApi ->
                repository.getMoviesByGenre(genreApi.id.toString(), 1).let { movie ->
                    movie.movies?.forEach {
                        if (it.genreIds?.get(0) == genreApi.id) {
                            genreApi.movies = movie.movies as MutableList<MovieRecycler>?
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

    fun getReleaseMovies() = CoroutineScope(Dispatchers.IO).launch {
        try {
            repository.getReleaseMovie().let { movie ->
                listReleaseMovie.postValue(movie.releaseMovies)
            }
        } catch (error: Throwable) {
            Log.e("Error", "Problema de Release $error")
        }
    }

    fun getPopularMovie() = CoroutineScope(Dispatchers.IO).launch {
        try {
            progress.postValue(true)
            repository.getPopularMovie().let {
                listPopularMovie.postValue(it.movie)
            }
        } catch (error: Throwable) {
            Log.e("Error", "Problema de Popular $error")
        } finally {
            progress.postValue(false)
        }
    }

}






