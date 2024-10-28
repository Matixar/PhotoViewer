package com.example.photoviewer.ui.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.photoviewer.model.Photo

@Composable
fun PhotoDetailsScreen(photo: Photo) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = photo.downloadUrl,
            contentDescription = "Photo ${photo.id}, by ${photo.author}",
            modifier = Modifier
                .animateContentSize()
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text("ID: ${photo.id}")
        Text("Author: ${photo.author}")
        Text("Dimensions: ${photo.width} x ${photo.height}")
        Text("Download URL: ${photo.downloadUrl}")
    }
}