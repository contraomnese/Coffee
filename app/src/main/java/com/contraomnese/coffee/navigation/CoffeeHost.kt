package com.contraomnese.coffee.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.contraomnese.coffee.MainActivityUiState
import com.contraomnese.coffee.core.ui.model.TopBarState
import com.contraomnese.coffee.core.ui.widgets.MapIcon
import com.contraomnese.coffee.core.ui.widgets.NavigationBackIcon
import com.contraomnese.coffee.core.ui.widgets.NotificationSnackBar
import com.contraomnese.coffee.core.ui.widgets.TopBar
import com.contraomnese.coffee.design.theme.padding40
import com.contraomnese.coffee.navigation.graphs.AuthGraph
import com.contraomnese.coffee.navigation.graphs.HomeGraph
import com.contraomnese.coffee.navigation.graphs.auth
import com.contraomnese.coffee.navigation.graphs.home

@Composable
internal fun CoffeeHost(
    navController: NavHostController = rememberNavController(),
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
    setTopBar: (TopBarState) -> Unit,
    uiState: MainActivityUiState,
    modifier: Modifier = Modifier,
) {

    Scaffold(
        topBar = {
            uiState.topBarState?.let { topBarState ->
                TopBar(
                    title = topBarState.title,
                    navigation = {
                        if (topBarState.showBackButton)
                            NavigationBackIcon(onBackClicked = {
                                topBarState.onBackClicked?.invoke() ?: navController.navigateUp()
                            })
                    },
                    action = {
                        if (topBarState.showActionButton) {
                            MapIcon(onClicked = { topBarState.onActionClicked?.invoke() })
                        }
                    }
                )
            }
        },
        snackbarHost = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(start = padding40, end = padding40, bottom = padding40)
            ) {
                SnackbarHost(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .zIndex(1f),
                    hostState = snackBarHostState,
                    snackbar = { snackBarData ->
                        NotificationSnackBar(message = snackBarData.visuals.message)
                    }
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavHost(
                navController,
                startDestination = if (uiState.shouldSkipAuth()) HomeGraph else AuthGraph,
                modifier = modifier,
            ) {
                auth(navController.authNavigator(), setTopBar)
                home(navController.homeNavigator(), setTopBar)
            }
        }
    }
}