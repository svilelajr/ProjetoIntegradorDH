package com.digitalhouse.moviewallet.model

class FavoriteList() {
    var name: List<MovieDetailResponse> = listOf()

    constructor(name: List<MovieDetailResponse>): this(){
        this.name = name
    }
}