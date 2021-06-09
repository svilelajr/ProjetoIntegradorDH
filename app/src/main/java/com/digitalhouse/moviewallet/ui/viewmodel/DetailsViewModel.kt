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
    val movieDetail by lazy { MutableLiveData<MovieDetailResponse>() }

    val providerFlatrate by lazy { MutableLiveData<List<Flatrate>?>() }
    val providerBuy by lazy { MutableLiveData<List<Buy>?>() }
    val providerRent by lazy { MutableLiveData<List<Rent>?>() }

    private val configuration = SingletonConfiguration.config
    private var popularityVote: String = ""
    val imageBackgroundUrl by lazy { MutableLiveData<String>() }
    val imagePosterUrl by lazy { MutableLiveData<String>() }


    fun getMovieDetail(movieId: String) = CoroutineScope(Dispatchers.IO).launch {
        try {
            repository.getMovieDetails(movieId).let { movie ->
                movieDetail.postValue(movie)
                imageBackgroundUrl.postValue(
                    "${configuration?.images?.base_url}${
                        configuration?.images?.backdrop_sizes?.get(1)
                    }${movie.backdropPath}"
                )
                imagePosterUrl.postValue(
                    "${configuration?.images?.base_url}${
                        configuration?.images?.poster_sizes?.get(4)
                    }${movie.posterPath}"
                )
            }
        } catch (error: Throwable) {
            Log.e("Error", "Problema de Conexão $error")
        }
    }

    fun getPopularity(): String {
        popularityVote = ((movieDetail.value?.voteAverage)?.times(10))?.toInt().toString()
        return popularityVote
    }

    fun getProvider(movieId: String) = CoroutineScope(Dispatchers.IO).launch {
        try {
            repository.getProviderMovie(movieId).let {
                providerFlatrate.postValue(it.providers?.bR?.flatrate)
                providerBuy.postValue(it.providers?.bR?.buy)
                providerRent.postValue(it.providers?.bR?.rent)
            }
        } catch (error: Throwable) {
            Log.e("Error", "Problema de Conexão $error")
        }
    }


}