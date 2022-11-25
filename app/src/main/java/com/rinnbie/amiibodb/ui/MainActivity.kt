package com.rinnbie.amiibodb.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.rinnbie.amiibodb.navigation.MyAppNavHost
import com.rinnbie.amiibodb.ui.theme.AmiiboDBTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AmiiboDBTheme {
                MyAppNavHost()
            }
        }
    }
}