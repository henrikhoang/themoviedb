package com.henrikhoang.themoviedb

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {

    @GET(".")
    suspend fun queryMovies(
        @Query("s") query: String,
        @Query("page") page: Int,
        @Query("type") type: String
    ): Response<MoviesResponse>
}