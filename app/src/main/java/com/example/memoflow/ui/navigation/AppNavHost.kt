package com.example.memoflow.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.memoflow.ui.screen.documents.DocumentDetailScreen
import com.example.memoflow.ui.screen.documents.DocumentsScreen
import com.example.memoflow.ui.screen.home.HomeScreen
import com.example.memoflow.ui.screen.search.SearchScreen
import com.example.memoflow.ui.screen.settings.SettingsScreen

@Composable
fun AppNavHost(){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Destinations.Home.route,
    ){
        composable(Destinations.Home.route) {
            HomeScreen(
                onGoDocument = {
                    navController.navigate(Destinations.Documents.route)
                },
                onOpenDetail = { id ->
                    navController.navigate(Destinations.Detail.createRoute(id))
                },
                onGoSearch = {
                    navController.navigate(Destinations.Search.route)
                },
                onGoSettings = {
                    navController.navigate(Destinations.Settings.route)
                },
            )
        }

        composable(Destinations.Documents.route) {
            DocumentsScreen(
                onBack = {navController.popBackStack()},
                onOpenDetail = {id->
                    navController.navigate(Destinations.Detail.createRoute(id))
                }
            )
        }

        composable(
            route = Destinations.Detail.route,
            arguments = listOf(
                navArgument("documentId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val documentId = backStackEntry.arguments?.getLong("documentId") ?: 0L
            DocumentDetailScreen(
                documentId = documentId,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Destinations.Search.route) {
            SearchScreen(
                onBack = { navController.popBackStack() },
                onOpenDetail = { id ->
                    navController.navigate(Destinations.Detail.createRoute(id))
                },
            )
        }

        composable(Destinations.Settings.route) {
            SettingsScreen(
                onBack = { navController.popBackStack() }
            )
        }


    }


}