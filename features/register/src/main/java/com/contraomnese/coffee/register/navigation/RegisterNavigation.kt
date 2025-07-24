package com.contraomnese.coffee.register.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.contraomnese.coffee.core.ui.model.TopBarState
import com.contraomnese.coffee.register.di.registerModule
import com.contraomnese.coffee.register.presentation.RegisterRoute
import com.contraomnese.coffee.register.presentation.RegisterViewModel
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.module.rememberKoinModules
import org.koin.core.annotation.KoinExperimentalAPI

@Serializable
private object RegisterDestination

@OptIn(KoinExperimentalAPI::class)
fun NavGraphBuilder.register(
    onNavigateBack: () -> Unit,
    setTopBar: (TopBarState) -> Unit,
) {

    composable<RegisterDestination> { backStackEntry ->

        rememberKoinModules(unloadOnForgotten = true) { listOf(registerModule) }

        val viewModel: RegisterViewModel = koinViewModel(viewModelStoreOwner = backStackEntry)

        RegisterRoute(viewModel = viewModel, onNavigateBack = onNavigateBack, setTopBar = setTopBar)
    }
}

fun NavHostController.navigateToRegister() {
    navigate(RegisterDestination)
}