@file:OptIn(ExperimentalMaterial3Api::class)

package com.contraomnese.coffee.core.ui.widgets

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.contraomnese.coffee.design.theme.CoffeeTheme


@Composable
fun TopBar(
    title: String,
    action: @Composable () -> Unit = {},
    navigation: @Composable () -> Unit = {},
    modifier: Modifier = Modifier,
) {

    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            Text(
                title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        navigationIcon = { navigation() },
        actions = { action() }
    )
}

@ExperimentalMaterial3Api
@Preview
@Composable
private fun TopBarPreview() {
    CoffeeTheme {
        TopBar(
            title = "Registration",
            navigation = {},
            action = {}
        )
    }
}

@ExperimentalMaterial3Api
@Preview
@Composable
private fun TopBarWithBackAndActionPreview() {
    CoffeeTheme {
        TopBar(
            title = "Menu",
            navigation = { NavigationBackIcon(onBackClicked = { })  },
            action = { MapIcon(onClicked = {})}
        )
    }
}