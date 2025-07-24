package com.contraomnese.coffee.menu.navigation

import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.contraomnese.coffee.core.ui.model.TopBarState
import com.contraomnese.coffee.menu.di.menuModule
import com.contraomnese.coffee.menu.presentation.MenuRoute
import com.contraomnese.coffee.menu.presentation.MenuViewModel
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.module.rememberKoinModules
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.parameter.parametersOf

@Serializable
private data class MenuDestination(val locationId: Int)

fun NavController.navigateToMenu(
    locationId: Int,
    navOptions: NavOptions? = null,
) {
    navigate(MenuDestination(locationId), navOptions)
}

@OptIn(KoinExperimentalAPI::class)
fun NavGraphBuilder.menu(
    onNavigateToBack: () -> Unit,
    onNavigateToOrder: () -> Unit,
    setTopBar: (TopBarState) -> Unit,
) {

    composable<MenuDestination> { backStackEntry ->

        rememberKoinModules(unloadOnForgotten = true) { listOf(menuModule) }

        val menuDestination = remember {
            backStackEntry.toRoute<MenuDestination>()
        }

        val viewModel: MenuViewModel = koinViewModel(
            viewModelStoreOwner = backStackEntry,
            parameters = {
                parametersOf(menuDestination.locationId)
            })

        MenuRoute(
            viewModel = viewModel,
            onNavigateToBack = onNavigateToBack,
            onNavigateToOrder = onNavigateToOrder,
            setTopBar = setTopBar
        )
    }
}