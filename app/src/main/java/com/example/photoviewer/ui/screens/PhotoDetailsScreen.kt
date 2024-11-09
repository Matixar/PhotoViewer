package com.example.photoviewer.ui.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.photoviewer.R
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
        Row {
            Text(stringResource(R.string.id), fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium)
            Text(photo.id, style = MaterialTheme.typography.bodyMedium)
        }
        Row {
            Text(stringResource(R.string.author), fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium)
            Text(photo.author, style = MaterialTheme.typography.bodyMedium)
        }
        Row {
            Text(stringResource(R.string.dimensions), fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium)
            Text("${photo.width} x ${photo.height}", style = MaterialTheme.typography.bodyMedium)
        }
        Row {
            Text(stringResource(R.string.url), fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium)
            Text(photo.downloadUrl, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Preview
@Composable
fun PhotoDetailsScreenPreview() {
    PhotoDetailsScreen(Photo("id", "author", 100, 100, "url"))
}