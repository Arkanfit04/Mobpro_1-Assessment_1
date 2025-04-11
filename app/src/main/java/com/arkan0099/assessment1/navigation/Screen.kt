package com.arkan0099.assessment1.navigation

sealed class Screen(val route: String) {
    data object Home: Screen("mainscreen")
    data object About: Screen("aboutscreen")
}