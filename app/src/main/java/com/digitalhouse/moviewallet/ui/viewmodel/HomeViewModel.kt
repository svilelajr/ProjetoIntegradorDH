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
    val loading = MutableLiveData<Boolean>()
    val listReleaseMovie = MutableLiveData<List<Movie>>()
    var genreApi = mutableListOf<Genre>()
    val genreView = listOf("28", "12", "35", "18", "10751", "27")

    fun getConfiguration() = CoroutineScope(Dispatchers.IO).launch {
        try {
            repository.getConfiguration().let { configuration ->
                SingletonConfiguration.setConfiguration(configuration)
                getGenre()
                getReleaseMovies()
            }
        } catch (error: Throwable) {
            Log.e("Error", "Problema de Configuration $error")
        }
    }

    fun getGenre() = CoroutineScope(Dispatchers.IO).launch {
        try {
            repository.getGenre().let {
                _listGenre.postValue(it.genres)
                genreApi = it.genres as MutableList<Genre>
                genreApi.forEach { genreApi ->
                    repository.getMoviesByGenre(genreApi.id.toString()).let { movie ->
                        genreApi.movies = movie.movies as MutableList<MovieRecycler>?
                    }
                }
            }

            listGenre.postValue(genreApi)

        } catch (error: Throwable) {
            Log.e("Error", "Problema de Genre $error")
        }
    }

    fun getMoviesByGenre(genreId: String?) = CoroutineScope(Dispatchers.IO).launch {
        repository.getMoviesByGenre(genreId).let { movie ->

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

    fun getGenreView(genre: Genre) {
        genreView.forEach { genreV ->
            if (genreV == genre.id.toString()) {
                _listGenre.postValue(listOf(genre))
            }
        }
    }
}






