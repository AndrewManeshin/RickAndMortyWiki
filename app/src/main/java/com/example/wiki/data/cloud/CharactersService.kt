package com.example.wiki.data.cloud

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface CharactersService {

    @GET("character")
    suspend fun fetchCharacters(
        @Query("page") page: Int,
        @Query("name") name: String,
        @Query("status") status: String
    ): ResponseBody
}