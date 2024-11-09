package com.example.photoviewer.repository

import com.example.photoviewer.model.Photo
import com.example.photoviewer.services.PhotoApiService
import javax.inject.Inject

class PhotoRepository @Inject constructor(private val apiService: PhotoApiService) {
    suspend fun getPhotos(page: Int): Result<List<Photo>> {
        val response = apiService.getPhotos(page)
        return if (response.isSuccessful && !response.body().isNullOrEmpty()) {
            Result.success(response.body()!!)
        } else {
            Result.failure(Exception("Failed to load photos"))
        }
    }
}