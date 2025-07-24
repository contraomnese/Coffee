package com.contraomnese.coffee.navigation

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.contraomnese.coffee.login.navigation.navigateToLogin
import com.contraomnese.coffee.map.navigation.navigateToMap
import com.contraomnese.coffee.menu.navigation.navigateToMenu
import com.contraomnese.coffee.navigation.graphs.AuthNavigator
import com.contraomnese.coffee.navigation.graphs.HomeNavigator
import com.contraomnese.coffee.order.navigation.navigateToOrder
import com.contraomnese.coffee.register.navigation.navigateToRegister

fun NavHostController.authNavigator(): AuthNavigator = object : AuthNavigator {

    override fun onNavigateToRegister() {
        navigateToRegister()
    }

    override fun onNavigateToLogin() {
        navigateToLogin()
    }

    override fun onNavigateUp() {
        popBackStack()
    }

}

fun NavHostController.homeNavigator(): HomeNavigator = object : HomeNavigator {

    override fun onNavigateToMap() {
        navigateToMap()
    }

    override fun onNavigateToMenu(locationId: Int) {
        navigateToMenu(locationId)
    }

    override fun onNavigateToOrder() {
        navigateToOrder()
    }


    override fun onNavigateUp() {
        popBackStack()
    }

}


fun NavHostController.navigateSingleTopTo(route: Any) {
    navigate(route) {
        popUpTo(this@navigateSingleTopTo.graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true

    }
}