package com.digitalhouse.moviewallet.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.digitalhouse.moviewallet.model.DiscoverMovies
import com.digitalhouse.moviewallet.model.MovieRecycler
import com.digitalhouse.moviewallet.repository.RepositoryMovie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContextViewModel : ViewModel() {

    private val repository = RepositoryMovie()
    val movieByCategory by lazy { MutableLiveData<MutableList<MovieRecycler>>() }


    fun getMovieListByGenre(genreId: String) = CoroutineScope(Dispatchers.IO).launch {
        repository.getMoviesByGenre(genreId).let {
            movieByCategory.postValue(it.movies as MutableList<MovieRecycler>?)
        }
    }

}