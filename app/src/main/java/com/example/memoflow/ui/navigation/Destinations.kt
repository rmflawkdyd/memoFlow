package com.example.memoflow.ui.navigation

sealed class Destinations(val route: String) {
    data object Home: Destinations("home")
    data object Search: Destinations("search")
    data object Settings: Destinations("settings")
    data object Add: Destinations("add")
    data object Detail: Destinations("detail/{documentId}"){
        fun createRoute(documentId: Long) = "detail/$documentId"
    }
}