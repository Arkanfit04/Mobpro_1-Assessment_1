package com.arkan0099.assessment1.navigation

import RecycleViewModel
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.arkan0099.assessment1.R
import com.arkan0099.assessment1.model.Profil
import com.arkan0099.assessment1.ui.screen.AboutScreen
import com.arkan0099.assessment1.ui.screen.DetailScreen
import com.arkan0099.assessment1.ui.screen.KEY_ID_CATATAN
import com.arkan0099.assessment1.ui.screen.MainScreen
import com.arkan0099.assessment1.ui.screen.RecycleBinScreen

@Composable
fun SetupNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route

    ) {
        composable(route = Screen.Home.route) {
            MainScreen(navController)
        }
        composable(route = Screen.FormBaru.route) {
            DetailScreen(navController)
        }
        composable(
            route = Screen.FormUbah.route,
            arguments = listOf(
                navArgument(KEY_ID_CATATAN) { type = NavType.LongType }
            )
        ) {navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getLong(KEY_ID_CATATAN)
            DetailScreen(navController, id)
        }
        composable(route =Screen.About.route) {
            AboutScreen(
                profil = Profil("profil", R.drawable.profil),
                navController = navController
            )
        }
        composable(route = Screen.RecycleBin.route) {
            val viewModel: RecycleViewModel = viewModel()
            RecycleBinScreen(viewModel = viewModel)

        }

    }
}