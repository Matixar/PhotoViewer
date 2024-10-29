package com.example.photoviewer

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import com.example.photoviewer.model.Photo
import com.example.photoviewer.repository.PhotoRepository
import com.example.photoviewer.viewmodels.PhotoViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyList
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class PhotoUnitTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()


    private val photoRepository = mockk<PhotoRepository>(relaxed = true)

    private lateinit var photoViewModel: PhotoViewModel

    @Mock
    private lateinit var photoListObserver: Observer<List<Photo>>

    @Mock
    private lateinit var loadingObserver: Observer<Boolean>

    @Mock
    private lateinit var errorObserver: Observer<String>

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        photoViewModel = PhotoViewModel(photoRepository, SavedStateHandle())

        photoViewModel.photoList.observeForever(photoListObserver)
        photoViewModel.isLoading.observeForever(loadingObserver)
        photoViewModel.error.observeForever(errorObserver)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        photoViewModel.photoList.removeObserver(photoListObserver)
        photoViewModel.isLoading.removeObserver(loadingObserver)
        photoViewModel.error.removeObserver(errorObserver)
    }

    @Test
    fun `fetchPhotos should update photoList on success`() = runTest {
        //Arrange
        val mockPhotos = listOf(
            Photo(id = "1", author = "Author1", width = 640, height = 480, downloadUrl = "url1"),
            Photo(id = "2", author = "Author2", width = 640, height = 480, downloadUrl = "url2")
        )
        coEvery { photoRepository.getPhotos(any()) } returns Response.success(mockPhotos)

        //Act
        photoViewModel.fetchPhotos()
        testDispatcher.scheduler.advanceUntilIdle()

        //Assert
        verify(loadingObserver).onChanged(true)
        verify(photoListObserver).onChanged(mockPhotos)
        verify(loadingObserver).onChanged(false)
        verify(errorObserver, never()).onChanged(anyString())
    }

    @Test
    fun `fetchPhotos shows error on failure`() = runTest {
        //Arrange
        coEvery { photoRepository.getPhotos(any()) } throws Exception()

        //Act
        photoViewModel.fetchPhotos()
        testDispatcher.scheduler.advanceUntilIdle()

        //Assert
        verify(loadingObserver).onChanged(true)
        verify(errorObserver).onChanged("Failed to load photos")
        verify(loadingObserver).onChanged(false)
        verify(photoListObserver, never()).onChanged(anyList())
    }


}