package com.example.compose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.compose.game.GameRoute
import com.example.compose.settings.SettingsRoute

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "game") {

        composable("game") {
            GameRoute(
                onOpenSettings = { navController.navigate("settings") }
            )
        }

        composable("settings") {
            SettingsRoute(
                onBack = { navController.popBackStack() }
            )
        }
    }
}