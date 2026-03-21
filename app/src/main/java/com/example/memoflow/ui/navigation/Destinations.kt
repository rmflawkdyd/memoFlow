package com.example.memoflow.ui.navigation

sealed class Destinations(val route: String) {
    data object Home: Destinations("home")
    data object Documents: Destinations("documents")
    data object Search: Destinations("search")
    data object Settings: Destinations("settings")
    data object Detail: Destinations("detail/{documentId}"){
        fun createRoute(documentId: Int) = "detail/$documentId"
    }
}