package com.rinnbie.amiibodb.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rinnbie.amiibodb.ui.amiibo.AmiiboListScreen
import com.rinnbie.amiibodb.ui.home.HomeScreen

@Composable
fun MyAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "home"
) {
    NavHost(
        modifier = modifier, navController = navController, startDestination = startDestination
    ) {
        composable("home") {
            HomeScreen(onNavigateToList = {
                navController.navigate("all_amiibos")
            })
        }
        composable("all_amiibos") {
            AmiiboListScreen(
                modifier = modifier
            )
        }
    }
}