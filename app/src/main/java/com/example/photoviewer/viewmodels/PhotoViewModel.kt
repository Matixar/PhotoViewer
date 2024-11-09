package com.example.photoviewer.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photoviewer.model.Photo
import com.example.photoviewer.repository.PhotoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import kotlinx.coroutines.launch
import kotlin.random.Random

@HiltViewModel
class PhotoViewModel @Inject constructor(
    private val repository: PhotoRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _photoList = MutableStateFlow(savedStateHandle.get("photoList") ?: emptyList<Photo>())
    val photoList: StateFlow<List<Photo>> = _photoList

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun fetchPhotos() {
        _isLoading.value = true
        val randomPage = Random.nextInt(1, 49) // Unsplash consists of 49 full pages of photos
        viewModelScope.launch {
            try {
                val result = repository.getPhotos(randomPage)
                if(result.isSuccess) {
                    val photos = result.getOrNull().orEmpty()
                    _photoList.value = photos
                } else {
                    _error.value = "Error: ${result.exceptionOrNull()?.message}"
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to load photos"
            }
            finally {
                _isLoading.value = false
            }
        }
    }
}