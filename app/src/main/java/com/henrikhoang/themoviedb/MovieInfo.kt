package com.henrikhoang.themoviedb

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoviesResponse(
    @SerialName("Search")
    val search: List<MovieInfo>? = null,
    @SerialName("totalResult")
    val totalResult: String? = null,
    @SerialName("Response")
    val response: String? = null
)

@Serializable
data class MovieInfo(
    @SerialName("Title")
    val title: String,
    @SerialName("Year")
    val year: String,
    @SerialName("imdbID")
    val imdbID: String,
    @SerialName("Type")
    val type: String,
    @SerialName("Poster")
    val poster: String,
)
