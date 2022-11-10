package com.rinnbie.amiibodb.ui.theme

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Immutable
data class BackgroundTheme(
    val colorScheme: ColorScheme = LightColors
)

val LocalBackgroundTheme = staticCompositionLocalOf { BackgroundTheme() }

@Composable
fun AmiiboDBBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Surface(
        color = LocalBackgroundTheme.current.colorScheme.background,
        modifier = modifier.fillMaxSize(),
    ) {
        content()
    }
}