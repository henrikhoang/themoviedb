package com.henrikhoang.themoviedb

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class Repository @Inject constructor(
    private val service: NetworkService,
    @ApplicationContext context: Context
) : BaseRepository(context) {

    suspend fun queryMovie(
        query: String,
        page: Int,
        type: String
    ) = dataCall {
        service.queryMovies(query, page, type)
    }

}