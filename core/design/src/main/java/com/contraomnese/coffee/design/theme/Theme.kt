package com.contraomnese.coffee.design.theme

import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext


private val coffeeScheme = darkColorScheme(
    primary = Color(0xFF342D1A),
    onPrimary = Color(0xFFF6E5D1),
    primaryContainer = Color(0xFF342D1A),
    onPrimaryContainer = Color(0xFFF6E5D1),
    background = Color(0xFFFFFFFF),
    onBackground = Color(0xFF000000),
    surface = Color(0xFFF6E5D1),
    onSurface = Color(0xFF846340),
    onSurfaceVariant = Color(0xFFAF9479),
    error = Color(0xFFFF453A),
    onError = Color(0xFFFFFFFF)
)

@Composable
fun CoffeeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && supportsDynamicTheming() -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        else -> coffeeScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = coffeeTypography,
        content = content
    )
}

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
fun supportsDynamicTheming() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S