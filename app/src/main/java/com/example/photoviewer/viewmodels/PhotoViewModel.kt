package com.example.photoviewer.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photoviewer.model.Photo
import com.example.photoviewer.repository.PhotoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import kotlin.random.Random

@HiltViewModel
class PhotoViewModel @Inject constructor(
    private val repository: PhotoRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _photoList = savedStateHandle.getLiveData<List<Photo>>("photoList")
    val photoList: LiveData<List<Photo>> get() = _photoList

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun fetchPhotos() {
        _isLoading.value = true
        val randomPage = Random.nextInt(1, 49) // Unsplash consists of 49 full pages of photos
        viewModelScope.launch {
            try {
                val response = repository.getPhotos(randomPage)
                if(response.isSuccessful) {
                    _photoList.value = response.body()
                    if(_photoList.value.isNullOrEmpty()) {
                        _error.value = "No photos found"
                    }
                } else {
                    _error.value = "Error: ${response.message()}"
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