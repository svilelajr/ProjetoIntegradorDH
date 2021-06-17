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
    val providerLogoPath = mutableListOf<String>()

    val providers by lazy { MutableLiveData<List<String>?>() }
    private val configuration = SingletonConfiguration.config
    val imageBackgroundUrl by lazy { MutableLiveData<String>() }
    val imagePosterUrl by lazy { MutableLiveData<String>() }
    val similarMovies = MutableLiveData<List<Movie>?>()


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

    fun getSimiliarMovie(movieId: String) = CoroutineScope(Dispatchers.IO).launch {
        try {
            repository.getSimiliarMovie(movieId).let {
                similarMovies.postValue(it.movies)
            }
        } catch (error: Throwable) {
            Log.e("Error", "Problema de Conexão $error")
        }
    }

    fun getProvider(movieId: String) = CoroutineScope(Dispatchers.IO).launch {
        try {
            repository.getProviderMovie(movieId).let { providerResponse ->
                providerResponse.providers?.bR?.flatrate?.forEach { providerF ->
                    providerF.logoPath?.let { it1 -> providerLogoPath.add(it1) }
                }
                providerResponse.providers?.bR?.buy?.forEach { providerF ->
                    providerF.logoPath?.let { it1 -> providerLogoPath.add(it1) }
                }
                providerResponse.providers?.bR?.rent?.forEach { providerF ->
                    providerF.logoPath?.let { it1 -> providerLogoPath.add(it1) }
                }
                val nProviders = providerLogoPath.distinct()
                providers.postValue(nProviders)
            }
        } catch (error: Throwable) {
            Log.e("Error", "Problema de Conexão $error")
        }
    }

}