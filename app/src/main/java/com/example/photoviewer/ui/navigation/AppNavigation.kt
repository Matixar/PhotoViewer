package com.example.photoviewer.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.photoviewer.ui.screens.PhotoDetailsScreen
import com.example.photoviewer.ui.screens.PhotoListScreen
import com.example.photoviewer.viewmodels.PhotoViewModel

@Composable
fun AppNavigation(photoViewModel: PhotoViewModel) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "photoList") {
        composable("photoList",
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(700)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(700)
                )
            }
        ) {
            PhotoListScreen(navController = navController, photoViewModel)
        }
        composable("photoDetails/{photoId}",
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(700)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(700)
                )
            }
            ) { backStackEntry ->
            val photoId = backStackEntry.arguments?.getString("photoId")
            val photo = photoViewModel.photoList.value?.find { it.id == photoId }
            photo?.let { PhotoDetailsScreen(it) }
        }
    }
}