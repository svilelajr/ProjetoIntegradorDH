package com.digitalhouse.moviewallet.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.digitalhouse.moviewallet.model.Genre
import com.digitalhouse.moviewallet.repository.RepositoryMovie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val repository = RepositoryMovie()


    val _listGenre = MutableLiveData<List<Genre>>()
    val listGenre: LiveData<List<Genre>> = _listGenre

    fun getGenre() = CoroutineScope(Dispatchers.IO).launch {
        try {
            repository.getGenre().let { genre ->
                _listGenre.postValue(genre.genres)
            }
        } catch (error: Throwable) {
            Log.e("Error", "Problema de conexão $error")
        }
    }
}