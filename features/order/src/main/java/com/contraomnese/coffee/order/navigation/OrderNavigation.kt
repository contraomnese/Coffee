package com.contraomnese.coffee.order.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.contraomnese.coffee.core.ui.model.TopBarState
import com.contraomnese.coffee.order.di.orderModule
import com.contraomnese.coffee.order.presentation.OrderRoute
import com.contraomnese.coffee.order.presentation.OrderViewModel
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.module.rememberKoinModules
import org.koin.core.annotation.KoinExperimentalAPI

@Serializable
private object OrderDestination

fun NavController.navigateToOrder() {
    navigate(OrderDestination)
}

@OptIn(KoinExperimentalAPI::class)
fun NavGraphBuilder.order(
    onNavigateToBack: () -> Unit,
    setTopBar: (TopBarState) -> Unit,
) {

    composable<OrderDestination> { backStackEntry ->

        rememberKoinModules(unloadOnForgotten = true) { listOf(orderModule) }

        val viewModel: OrderViewModel = koinViewModel(viewModelStoreOwner = backStackEntry,)

        OrderRoute(
            viewModel = viewModel,
            onNavigateToBack = onNavigateToBack,
            setTopBar = setTopBar
        )
    }
}