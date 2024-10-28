package com.example.photoviewer.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.photoviewer.R
import com.example.photoviewer.model.Photo
import com.example.photoviewer.viewmodels.PhotoViewModel

@Composable
fun PhotoListScreen(navController: NavController, photoViewModel: PhotoViewModel) {
    val photoList by photoViewModel.photoList.observeAsState(emptyList())
    val error by photoViewModel.error.observeAsState()
    val isLoading by photoViewModel.isLoading.observeAsState(false)

    var isRefreshPressed by remember { mutableStateOf(false) }
    val angle by animateFloatAsState(
        targetValue = if (isRefreshPressed) 360f else 0f,
        animationSpec = tween(600),
        label = "spin"
    )

    LaunchedEffect(Unit) {
        if (photoList.isEmpty()) {
            photoViewModel.fetchPhotos()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            onClick = {
                photoViewModel.fetchPhotos()
                isRefreshPressed = !isRefreshPressed
            }
        ) {
            Image(
                painter = painterResource(R.drawable.refresh), contentDescription = "Refresh",
                modifier = Modifier.rotate(angle)
            )
        }

        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {

            LazyColumn {
                items(photoList) { photo ->
                    PhotoListItem(photo, navController)
                }
            }

            error?.let {
                Text(it, color = Color.Red)
            }
        }
    }
}

@Composable
fun PhotoListItem(photo: Photo, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navController.navigate("photoDetails/${photo.id}") }
            .padding(16.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(photo.downloadUrl)
                .crossfade(true)
                .build(),
            contentDescription = "Photo ${photo.id}, by ${photo.author}",

            modifier = Modifier.size(100.dp)
        )
        Spacer(modifier = Modifier.width(32.dp))
        Text(text = "ID = ${photo.id}", modifier = Modifier.align(Alignment.CenterVertically))
    }
    HorizontalDivider(thickness = 2.dp)
}