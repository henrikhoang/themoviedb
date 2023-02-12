package com.henrikhoang.themoviedb

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

@Module
@InstallIn(ActivityComponent::class)
class NetworkModule {

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    fun provideService(
        @ApplicationContext context: Context,
        client: OkHttpClient,
        json: Json
    ): NetworkService {
        return Retrofit.Builder()
            .baseUrl(context.getString(R.string.base_url))
            .client(client)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(NetworkService::class.java)
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }
}