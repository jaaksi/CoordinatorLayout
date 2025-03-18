package com.example.coordinatorlayoutdemo.ui

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.coordinatorlayoutdemo.data.MenuDataProvider

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController,
        startDestination = "home",
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(300)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(300)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(300)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(300)
            )
        }
    ) {
        composable("home") {
            HomeScreen {
                navController.navigate(it)
            }
        }
        MenuDataProvider.menuData.forEachIndexed { index, item ->
            composable(item.route) {
                when (index) {
                    0 -> SimpleScreen1()
                    1 -> SimpleScreen2()
                    2 -> SimpleScreen3()
                    3 -> SimpleScreen6()
                    4 -> SimpleScreen4()
                    5 -> SimpleScreen5()
                }
            }
        }
    }
}