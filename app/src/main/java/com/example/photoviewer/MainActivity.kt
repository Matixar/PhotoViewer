package com.example.photoviewer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.photoviewer.ui.navigation.AppNavigation
import com.example.photoviewer.viewmodels.PhotoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: PhotoViewModel = viewModel()
            AppNavigation(viewModel)
        }
    }
}
