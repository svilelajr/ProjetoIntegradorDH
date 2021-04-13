package com.digitalhouse.moviewallet.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.digitalhouse.moviewallet.adapters.HomeScreenCategoryAdapter
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
    val listMovie = MutableLiveData<List<Movie>>()
    private var genreId = getGenreId()




    init {
        getConfiguration()
        getGenre()
        getReleaseMovies()
        getMoviesByGenre(genreId)

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

    fun getMoviesByGenre(genreId: String) = CoroutineScope(Dispatchers.IO).launch {
        try {
            repository.getMoviesByGenre(genreId).let { movie ->
                listMovie.postValue(movie.movies)
            }
        } catch (error: Throwable) {
            Log.e("Error", "Problema de conexão $error")
        }
    }

    fun getGenreId(): String {
        var id:String = ""
        listGenre.value?.forEach { id = it.id.toString()}

        return id
    }

}

