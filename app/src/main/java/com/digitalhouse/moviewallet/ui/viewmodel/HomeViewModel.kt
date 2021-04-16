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
    val _listGenre = MutableLiveData<List<Genre>>()
    val listGenre: LiveData<List<Genre>> = _listGenre
    val loading = MutableLiveData<Boolean>()
    val listReleaseMovie = MutableLiveData<List<Movie>>()
    var genreApi = mutableListOf<Genre>()
    val genreView = listOf("28", "12", "35", "18", "10751", "27")

    fun getConfiguration() = CoroutineScope(Dispatchers.IO).launch {
        loading.postValue(true)
        try {
            repository.getConfiguration().let { configuration ->
                SingletonConfiguration.setConfiguration(configuration)
                getGenre()
                getReleaseMovies()
                loading.postValue(false)
            }
        } catch (error: Throwable) {
            loading.postValue(false)
            Log.e("Error", "Problema de Configuration $error")
        }
    }

    fun getGenre() = CoroutineScope(Dispatchers.IO).launch {
        loading.postValue(true)
        try {
            repository.getGenre().let {
                _listGenre.postValue(it.genres)
                genreApi = it.genres as MutableList<Genre>
                genreApi.forEach {
                    repository.getMoviesByGenre(it.id.toString()).let { movie ->
                        _listGenre.value?.forEach {
                            it.movies = movie.movies as MutableList<Movie>?
                        }
                    }
                }
            }
        } catch (error: Throwable) {
            loading.postValue(false)
            Log.e("Error", "Problema de Genre $error")
        }
    }

    fun getMoviesByGenre(genreId: String?) = CoroutineScope(Dispatchers.IO).launch {
        repository.getMoviesByGenre(genreId).let { movie ->

        }
    }

    fun getReleaseMovies() = CoroutineScope(Dispatchers.IO).launch {
        loading.postValue(true)
        try {
            repository.getReleaseMovie().let { movie ->
                listReleaseMovie.postValue(movie.releaseMovies)
                loading.postValue(false)
            }
        } catch (error: Throwable) {
            loading.postValue(false)
            Log.e("Error", "Problema de Release $error")
        }
    }

    fun getGenreView(genre: Genre) {
        genreView.forEach { genreV ->
            if (genreV == genre.id.toString()) {
                _listGenre.postValue(listOf(genre))
            }
        }
    }

    fun addMovie() {

    }
}






