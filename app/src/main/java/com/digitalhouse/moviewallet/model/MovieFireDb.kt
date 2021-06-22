package com.digitalhouse.moviewallet.model

class MovieFireDb() {
    var adult: Boolean? = null
    private var backdropPath: String? = null
    private var belongsToCollection: BelongsToCollection? = null
    private var budget: Int? = null
    private var genres: List<Genre>? = null
    private var homepage: String? = null
    var id: Int? = null
    var imdbId: String? = null
    private var originalLanguage: String? = null
    private var originalTitle: String? = null
    private var overview: String? = null
    private var popularity: Double? = null
    var posterPath: String? = null
    private var productionCompanies: List<ProductionCompany>? = null
    private var productionCountries: List<ProductionCountry>? = null
    private var releaseDate: String? = null
    private var revenue: Int? = null
    private var runtime: Int? = null
    private var spokenLanguages: List<SpokenLanguage>? = null
    private var status: String? = null
    private var tagline: String? = null
    var title: String? = null
    private var video: Boolean? = null
    private var voteAverage: Double? = null
    private var voteCount: Int? = null

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