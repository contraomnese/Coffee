package com.contraomnese.coffee.map.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.contraomnese.coffee.core.ui.model.TopBarState
import com.contraomnese.coffee.map.di.mapModule
import com.contraomnese.coffee.map.presentation.MapRoute
import com.contraomnese.coffee.map.presentation.MapViewModel
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.module.rememberKoinModules
import org.koin.core.annotation.KoinExperimentalAPI

@Serializable
private object MapDestination

fun NavController.navigateToMap() {
    navigate(MapDestination)
}

@OptIn(KoinExperimentalAPI::class)
fun NavGraphBuilder.map(
    onNavigateToBack: () -> Unit,
    onNavigateToMenu: (Int) -> Unit,
    setTopBar: (TopBarState) -> Unit,
) {

    composable<MapDestination> { backStackEntry ->

        rememberKoinModules(unloadOnForgotten = true) { listOf(mapModule) }

        val viewModel: MapViewModel = koinViewModel(viewModelStoreOwner = backStackEntry,)

        MapRoute(
            viewModel = viewModel,
            onNavigateToBack = onNavigateToBack,
            setTopBar = setTopBar,
            onNavigateToMenu = onNavigateToMenu
        )
    }
}