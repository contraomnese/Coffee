package com.contraomnese.coffee.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.navigation
import com.contraomnese.coffee.core.ui.model.TopBarState
import com.contraomnese.coffee.login.navigation.LoginDestination
import com.contraomnese.coffee.login.navigation.login
import com.contraomnese.coffee.register.navigation.register
import kotlinx.serialization.Serializable

@Serializable
object AuthGraph

interface AuthNavigator {
    fun onNavigateToRegister()
    fun onNavigateToLogin()
    fun onNavigateUp()
}

fun NavGraphBuilder.auth(
    externalNavigator: AuthNavigator,
    setTopBar: (TopBarState) -> Unit,
) {

    navigation<AuthGraph>(startDestination = LoginDestination) {
        login(onNavigateToRegister = externalNavigator::onNavigateToRegister, setTopBar)
        register(onNavigateBack = externalNavigator::onNavigateUp, setTopBar)
    }
}