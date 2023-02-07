package com.example.artabookadvancelevel.api

import com.example.artabookadvancelevel.model.ImageResponse
import com.example.artabookadvancelevel.util.Util
import okhttp3.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApi {
    @GET("/api/")
    suspend fun imageSearch(
        @Query("q") searchQuery:String,
        @Query("key") apiKey:String= Util.API_KEY
    ):retrofit2.Response<ImageResponse>
}