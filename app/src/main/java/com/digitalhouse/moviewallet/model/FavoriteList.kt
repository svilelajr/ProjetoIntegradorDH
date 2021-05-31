package com.digitalhouse.moviewallet.model

class FavoriteList() {
    var name: List<MovieDetail> = listOf()

    constructor(name: List<MovieDetail>): this(){
        this.name = name
    }
}