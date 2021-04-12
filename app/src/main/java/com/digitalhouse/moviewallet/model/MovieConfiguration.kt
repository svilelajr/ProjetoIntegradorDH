package com.digitalhouse.moviewallet.model

data class MovieConfiguration(
    val change_keys: List<String>? = listOf(),
    val images: Images? = Images()
)