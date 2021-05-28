package com.digitalhouse.moviewallet.model

class MovieFireDb() {
    var adult: Boolean? = null
    var backdropPath: String? = null
    var belongsToCollection: BelongsToCollection? = null
    var budget: Int? = null
    var genres: List<Genre>? = null
    var homepage: String? = null
    var id: Int? = null
    var imdbId: String? = null
    var originalLanguage: String? = null
    var originalTitle: String? = null
    var overview: String? = null
    var popularity: Double? = null
    var posterPath: String? = null
    var productionCompanies: List<ProductionCompany>? = null
    var productionCountries: List<ProductionCountry>? = null
    var releaseDate: String? = null
    var revenue: Int? = null
    var runtime: Int? = null
    var spokenLanguages: List<SpokenLanguage>? = null
    var status: String? = null
    var tagline: String? = null
    var title: String? = null
    var video: Boolean? = null
    var voteAverage: Double? = null
    var voteCount: Int? = null

    constructor(
        adult: Boolean?,
        backdropPath: String?,
        belongsToCollection: BelongsToCollection?,
        budget: Int?,
        genres: List<Genre>?,
        homepage: String?,
        id: Int?,
        imdbId: String?,
        originalLanguage: String?,
        originalTitle: String?,
        overview: String?,
        popularity: Double?,
        posterPath: String?,
        productionCompanies: List<ProductionCompany>?,
        productionCountries: List<ProductionCountry>?,
        releaseDate: String?,
        revenue: Int?,
        runtime: Int?,
        spokenLanguages: List<SpokenLanguage>?,
        status: String?,
        tagline: String?,
        title: String?,
        video: Boolean?,
        voteAverage: Double?,
        voteCount: Int?
    ) : this() {

        this.adult = adult
        this.backdropPath = backdropPath
        this.belongsToCollection = belongsToCollection
        this.budget = budget
        this.genres = genres
        this.homepage = homepage
        this.id = id
        this.imdbId = imdbId
        this.originalLanguage = originalLanguage
        this.originalTitle = originalTitle
        this.overview = overview
        this.popularity = popularity
        this.posterPath = posterPath
        this.productionCompanies = productionCompanies
        this.productionCountries = productionCountries
        this.releaseDate = releaseDate
        this.revenue = revenue
        this.runtime = runtime
        this.spokenLanguages = spokenLanguages
        this.status = status
        this.tagline = tagline
        this.title = title
        this.video = video
        this.voteAverage = voteAverage
        this.voteCount = voteCount
    }
}