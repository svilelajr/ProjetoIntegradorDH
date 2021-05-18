package com.digitalhouse.moviewallet.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.digitalhouse.moviewallet.model.*
import com.digitalhouse.moviewallet.repository.RepositoryMovie
import com.digitalhouse.moviewallet.repository.SingletonConfiguration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsViewModel : ViewModel() {

    private val repository = RepositoryMovie()
    val movieDetail by lazy { MutableLiveData<MovieDetail>() }

    val providerMovie by lazy { MutableLiveData<List<Flatrate>?>() }
    private val configuration = SingletonConfiguration.config
    private var imageUrl: String = ""
    private var popularityVote: String = ""
    val imageTest by lazy { MutableLiveData<String>() }

    fun getMovieDetail(movieId: String) = CoroutineScope(Dispatchers.IO).launch {
        try {
            repository.getMovieDetails(movieId).let {movie->
                movieDetail.postValue(movie as MovieDetail)
                repository.getImageMovie(movie.id.toString()).let {imageResponse->
                    imageResponse.backdrops?.forEach { bd ->
                        if (bd.iso6391 != "pt") {
                            imageTest.postValue(
                                "${configuration?.images?.base_url}${configuration?.images?.backdrop_sizes?.get(1)}${movie.backdropPath}"
                            )
                        } else {
                            val tes = "${configuration?.images?.base_url}${
                                configuration?.images?.backdrop_sizes?.get(1)
                            }${imageResponse.backdrops?.last()?.filePath}"
                            imageTest.postValue(tes)
                        }
                    }

                }
            }
        } catch (error: Throwable) {
            Log.e("Error", "Problema de Conexão $error")
        }
    }

//    fun getCreditMovie(movieId: String) = CoroutineScope(Dispatchers.IO).launch {
//        try {
//            repository.getCreditMovie(movieId).let {
//                actorMovie.postValue(it.cast)
//            }
//        } catch (error: Throwable) {
//            Log.e("Error", "Problema de Conexão $error")
//        }
//    }

    fun getBackdropPath(): String {
        imageUrl =
            "${configuration?.images?.base_url}${configuration?.images?.poster_sizes?.last()}${movieDetail.value?.backdropPath}"
        return imageUrl
    }

    fun getPopularity(): String {
        popularityVote = ((movieDetail.value?.voteAverage)?.times(10))?.toInt().toString()
        return popularityVote
    }

    fun getProvider(movieId: String) = CoroutineScope(Dispatchers.IO).launch {
        try {
            repository.getProviderMovie(movieId).let {
                providerMovie.postValue(it.providers?.bR?.flatrate)
            }
        } catch (error: Throwable) {
            Log.e("Error", "Problema de Conexão $error")

        }
    }


}