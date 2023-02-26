package com.rinnbie.amiibodb.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.rinnbie.amiibodb.R
import com.rinnbie.amiibodb.ui.amiibo.AmiiboDetailRoute
import com.rinnbie.amiibodb.ui.amiibo.AmiiboListRoute
import com.rinnbie.amiibodb.ui.home.HomeRoute

@Composable
fun MyAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = "home"
) {
    NavHost(
        modifier = modifier, navController = navController, startDestination = startDestination
    ) {
        composable("home") {
            HomeRoute(
                onNavigateToList = { arg ->
                    val route = if (arg == "all") "list/all" else "list/$arg"
                    Log.d("MyAppNavHost", "navigate to $route")
                    navController.navigate(route = route)
                }, onAmiiboClick = { id ->
                    navController.navigate(route = "amiibo/$id")
                })
        }
        composable(route = "list/{seriesArg}",
            arguments = listOf(
                navArgument("seriesArg") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val seriesName = backStackEntry.arguments?.getString("seriesArg")
            AmiiboListRoute(
                modifier = modifier,
                title = if (seriesName == "all") stringResource(id = R.string.all) else seriesName,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onAmiiboClick = { id ->
                    navController.navigate(route = "amiibo/$id")
                }
            )
        }

        composable(
            route = "amiibo/{id}",
            arguments = listOf(
                navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id").orEmpty()
            AmiiboDetailRoute(
                modifier = modifier,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}