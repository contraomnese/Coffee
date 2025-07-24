package com.contraomnese.coffee.core.ui.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import com.contraomnese.coffee.design.DevicePreviews
import com.contraomnese.coffee.design.theme.CoffeeTheme
import com.contraomnese.coffee.design.theme.itemWidth3
import com.contraomnese.coffee.design.theme.itemWidth64

@Composable
fun LoadingIndicator(modifier: Modifier = Modifier) {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = modifier.align(Alignment.Center).width(itemWidth64),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.onPrimary,
            strokeWidth = itemWidth3,
            strokeCap = StrokeCap.Square
        )
    }

}

@DevicePreviews
@Composable
fun LoadingIndicatorPreview(modifier: Modifier = Modifier) {
    CoffeeTheme {
        LoadingIndicator()
    }
}