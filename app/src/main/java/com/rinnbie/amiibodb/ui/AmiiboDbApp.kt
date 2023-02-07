package com.rinnbie.amiibodb.ui

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rinnbie.amiibodb.navigation.MyAppNavHost
import com.rinnbie.amiibodb.ui.theme.AmiiboDBTheme
import com.rinnbie.amiibodb.util.NetworkMonitor

@OptIn(
    ExperimentalMaterial3Api::class,
)
@Composable
fun AmiiboDbApp(
    networkMonitor: NetworkMonitor,
    appState: AmiiboDbAppState = rememberAmiiboDbAppState(
        networkMonitor = networkMonitor
    )
) {
    AmiiboDBTheme {
        val snackbarHostState = remember { SnackbarHostState() }
        val isOffline by appState.isOffline.collectAsStateWithLifecycle()
        val notConnectedMessage = "You aren't connected to the internet"

        LaunchedEffect(isOffline) {
            Log.d("AmiiboDbApp", "isOffline=$isOffline")
            if (isOffline) {
                snackbarHostState.showSnackbar(
                    message = notConnectedMessage,
                    duration = SnackbarDuration.Indefinite
                )
            }
        }

        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        ) { padding ->
            MyAppNavHost(
                modifier = Modifier.padding(padding),
                navController = appState.navController
            )
        }
    }
}