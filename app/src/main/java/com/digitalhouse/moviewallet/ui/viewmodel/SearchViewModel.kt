package com.digitalhouse.moviewallet.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.digitalhouse.moviewallet.model.Genre
import com.digitalhouse.moviewallet.model.Movie
import com.digitalhouse.moviewallet.repository.RepositoryMovie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    private val repository = RepositoryMovie()

    private val _listGenre = MutableLiveData<List<Genre>>()
    val listGenre: LiveData<List<Genre>> = _listGenre

    val resultSearchMovie = MutableLiveData<List<Movie>>()


    fun getGenre() = CoroutineScope(Dispatchers.IO).launch {
        try {
            repository.getGenre().let { genre ->
                _listGenre.postValue(genre.genres)
            }
        } catch (error: Throwable) {
            Log.e("Error", "Problema de conexão $error")
        }
    }

    fun searchMovie(query: String?) = CoroutineScope(Dispatchers.IO).launch {
        try {
            if (query != null) {
                repository.getSearchMovie(query).let {
                    resultSearchMovie.postValue(it.movies)
                }
            }
        } catch (error: Throwable) {
            Log.e("Error", "Problema de conexão $error")

        }
    }
}