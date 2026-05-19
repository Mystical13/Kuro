package com.kurostream.tv.data.remote.metadata

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MetadataApi {
    @GET("meta/movie/{id}.json")
    suspend fun getCinemetaMovie(@Path("id") id: String): String // TODO: PHASE_3 Add data classes

    @POST("graphql")
    suspend fun getKitsuMetadata(@Body query: Map<String, String>): String // TODO: PHASE_3 Add data classes
}
