package com.kurostream.tv.di

import com.kurostream.tv.data.remote.metadata.MetadataApi
import com.kurostream.tv.data.remote.trailer.TrailerApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideMetadataApi(client: OkHttpClient): MetadataApi {
        return Retrofit.Builder()
            .baseUrl("https://v3-cinemeta.strem.io/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MetadataApi::class.java)
    }

    @Provides
    @Singleton
    fun provideTrailerApi(client: OkHttpClient): TrailerApi {
        return Retrofit.Builder()
            .baseUrl("https://www.googleapis.com/youtube/v3/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TrailerApi::class.java)
    }
}
