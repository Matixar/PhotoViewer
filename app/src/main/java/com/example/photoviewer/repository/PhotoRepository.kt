package com.example.photoviewer.repository

import com.example.photoviewer.services.PhotoApiService
import javax.inject.Inject

class PhotoRepository @Inject constructor(private val apiService: PhotoApiService) {
    suspend fun getPhotos(page: Int) = apiService.getPhotos(page)
}