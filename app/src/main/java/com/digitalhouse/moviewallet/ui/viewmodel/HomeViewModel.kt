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
    val listGenre:LiveData<List<Genre>> = _listGenre
    val listReleaseMovie = MutableLiveData<List<Movie>>()

    init {
        getConfiguration()
        getGenre()
        getReleaseMovies()
    }

    fun getConfiguration() = CoroutineScope(Dispatchers.IO).launch {
        try {
            repository.getConfiguration().let { configuration ->
                SingletonConfiguration.setConfiguration(configuration)
            }
        } catch (error: Throwable) {
            Log.e("Error", "Problema de conexão $error")
        }
    }

    fun getGenre() = CoroutineScope(Dispatchers.IO).launch {
        try {
            repository.getGenre().let { genre ->
                _listGenre.postValue(genre.genres)
            }
        } catch (error: Throwable) {
            Log.e("Error", "Problema de conexão $error")
        }
    }

    fun getReleaseMovies() = CoroutineScope(Dispatchers.IO).launch {
        try {
            repository.getReleaseMovie().let { movie ->
                listReleaseMovie.postValue(movie.releaseMovies)
            }
        } catch (error: Throwable) {
            Log.e("Error", "Problema de conexão $error")
        }
    }
}