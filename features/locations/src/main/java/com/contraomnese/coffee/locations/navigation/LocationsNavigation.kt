package com.contraomnese.coffee.locations.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.contraomnese.coffee.core.ui.model.TopBarState
import com.contraomnese.coffee.locations.di.locationsModule
import com.contraomnese.coffee.locations.presentation.LocationsRoute
import com.contraomnese.coffee.locations.presentation.LocationsViewModel
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.module.rememberKoinModules
import org.koin.core.annotation.KoinExperimentalAPI

@Serializable
object LocationsDestination

@OptIn(KoinExperimentalAPI::class)
fun NavGraphBuilder.locations(
    onNavigateToMap: () -> Unit,
    onNavigateToMenu: (Int) -> Unit,
    setTopBar: (TopBarState) -> Unit,
) {

    composable<LocationsDestination> { backStackEntry ->

        rememberKoinModules(unloadOnForgotten = true) { listOf(locationsModule) }

        val viewModel: LocationsViewModel = koinViewModel(viewModelStoreOwner = backStackEntry)

        LocationsRoute(
            viewModel = viewModel,
            setTopBar = setTopBar,
            onNavigateToMap = onNavigateToMap,
            onNavigateToMenu = onNavigateToMenu,
        )
    }
}