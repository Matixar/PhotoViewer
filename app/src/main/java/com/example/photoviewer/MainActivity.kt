package com.example.photoviewer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.photoviewer.ui.navigation.AppNavigation
import com.example.photoviewer.ui.theme.AppTheme
import com.example.photoviewer.viewmodels.PhotoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: PhotoViewModel = viewModel()
            AppTheme {
                Surface(tonalElevation = 5.dp) {
                    AppNavigation(viewModel)
                }
            }
        }
    }
}
