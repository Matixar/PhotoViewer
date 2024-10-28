package com.example.photoviewer.services

import com.example.photoviewer.model.Photo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotoApiService {
    @GET("v2/list?limit=20")
    suspend fun getPhotos(@Query("page") page: Int): Response<List<Photo>>
}