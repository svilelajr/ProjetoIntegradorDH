package com.digitalhouse.moviewallet.model



class User() {
    var email: String = ""
    var name: String = ""
    var subject: Subject? = null
    var movies: FavoriteList? = null

    constructor(email: String, name: String, subject: Subject, movies: FavoriteList?) : this() {
        this.email = email
        this.name = name
        this.subject = subject
        this.movies = movies
    }

}