package com.contraomnese.coffee.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.navigation
import com.contraomnese.coffee.core.ui.model.TopBarState
import com.contraomnese.coffee.locations.navigation.LocationsDestination
import com.contraomnese.coffee.locations.navigation.locations
import com.contraomnese.coffee.map.navigation.map
import com.contraomnese.coffee.menu.navigation.menu
import com.contraomnese.coffee.order.navigation.order
import kotlinx.serialization.Serializable

@Serializable
object HomeGraph

interface HomeNavigator {
    fun onNavigateToMap()
    fun onNavigateToMenu(locationId: Int)
    fun onNavigateToOrder()
    fun onNavigateUp()
}

fun NavGraphBuilder.home(
    externalNavigator: HomeNavigator,
    setTopBar: (TopBarState) -> Unit,
) {

    navigation<HomeGraph>(startDestination = LocationsDestination) {
        locations(onNavigateToMap = externalNavigator::onNavigateToMap, onNavigateToMenu = externalNavigator::onNavigateToMenu, setTopBar)
        menu(onNavigateToBack = externalNavigator::onNavigateUp, onNavigateToOrder = externalNavigator::onNavigateToOrder, setTopBar)
        order(onNavigateToBack = externalNavigator::onNavigateUp, setTopBar)
        map(onNavigateToBack = externalNavigator::onNavigateUp, onNavigateToMenu = externalNavigator::onNavigateToMenu, setTopBar)
    }
}