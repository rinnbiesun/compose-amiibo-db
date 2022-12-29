package com.rinnbie.amiibodb.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rinnbie.amiibodb.R
import com.rinnbie.amiibodb.ui.amiibo.AmiiboListRoute
import com.rinnbie.amiibodb.ui.home.HomeRoute

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
            HomeRoute(onNavigateToList = { arg ->
                val route = if (arg == "all") "list/all" else "list/$arg"
                Log.d("MyAppNavHost", "navigate to $route")
                navController.navigate(route = route)
            })
        }
        composable(route = "list/{seriesArg}",
            arguments = listOf(
                navArgument("seriesArg") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val seriesName = backStackEntry.arguments?.getString("seriesArg")
            AmiiboListRoute(
                title = if (seriesName == "all") stringResource(id = R.string.all) else seriesName,
                modifier = modifier,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}