package com.digitalhouse.moviewallet.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.digitalhouse.moviewallet.model.Actor
import com.digitalhouse.moviewallet.model.Movie
import com.digitalhouse.moviewallet.model.MovieDetail
import com.digitalhouse.moviewallet.repository.RepositoryMovie
import com.digitalhouse.moviewallet.repository.SingletonConfiguration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsViewModel: ViewModel() {

    private val repository = RepositoryMovie()
    val movieDetail by lazy{ MutableLiveData<MovieDetail>() }
    val actorMovie by lazy { MutableLiveData<List<Actor>>() }
    val configuration = SingletonConfiguration.config
    var imageUrl: String = ""

    fun getMovieDetail(movieId: String) = CoroutineScope(Dispatchers.IO).launch {
        try {
            repository.getMovieDetails(movieId).let {
                movieDetail.postValue(it as MovieDetail)
            }

        } catch (error: Throwable){
            Log.e("Error", "Problema de Conexão $error")
        }
    }

    fun getCreditMovie(movieId: String) = CoroutineScope(Dispatchers.IO).launch {
        try {
            repository.getCreditMovie(movieId).let {
                actorMovie.postValue(it.cast)
            }

        } catch (error: Throwable){
            Log.e("Error", "Problema de Conexão $error")
        }
    }

    fun getBackdropPath(): String{
      imageUrl = "${configuration?.images?.base_url}${configuration?.images?.poster_sizes?.last()}${movieDetail.value?.backdropPath}"
        return imageUrl
    }


}