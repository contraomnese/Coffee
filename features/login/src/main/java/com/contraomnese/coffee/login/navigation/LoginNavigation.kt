package com.contraomnese.coffee.login.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.contraomnese.coffee.core.ui.model.TopBarState
import com.contraomnese.coffee.login.di.loginModule
import com.contraomnese.coffee.login.presentation.LoginRoute
import com.contraomnese.coffee.login.presentation.LoginViewModel
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.module.rememberKoinModules
import org.koin.core.annotation.KoinExperimentalAPI

@Serializable
object LoginDestination

@OptIn(KoinExperimentalAPI::class)
fun NavGraphBuilder.login(
    onNavigateToRegister: () -> Unit,
    setTopBar: (TopBarState) -> Unit,
) {

    composable<LoginDestination> { backStackEntry ->

        rememberKoinModules(unloadOnForgotten = true) { listOf(loginModule) }

        val viewModel: LoginViewModel = koinViewModel(viewModelStoreOwner = backStackEntry)

        LoginRoute(
            viewModel = viewModel,
            onNavigateToRegister = onNavigateToRegister,
            setTopBar = setTopBar
        )
    }
}

fun NavHostController.navigateToLogin() {
    navigate(LoginDestination)
}