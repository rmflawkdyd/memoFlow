package com.example.memoflow.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.memoflow.ui.screen.add.AddDocumentScreen
import com.example.memoflow.ui.screen.documents.DocumentDetailScreen
import com.example.memoflow.ui.screen.home.HomeScreen
import com.example.memoflow.ui.screen.search.SearchScreen
import com.example.memoflow.ui.screen.settings.SettingsScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
){

    NavHost(
        navController = navController,
        startDestination = Destinations.Home.route,
        modifier= modifier
    ){
        composable(Destinations.Home.route) {
           HomeScreen(
               onAddClick = {
                   navController.navigate(Destinations.Add.route)
               },
               onDocumentClick = { documentId ->
                   navController.navigate(Destinations.Detail.createRoute(documentId))

               }
           )
        }


        composable(Destinations.Add.route) {
            AddDocumentScreen(
                onBack = { navController.popBackStack() },
                onSaved = {
                    navController.popBackStack()
                },
                )
        }

        composable(
            route = Destinations.Detail.route,
            arguments = listOf(
                navArgument("documentId") { type = NavType.LongType }
            )
        ) {
            DocumentDetailScreen(
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