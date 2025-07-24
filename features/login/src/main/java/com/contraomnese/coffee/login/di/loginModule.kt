package com.contraomnese.coffee.login.di

import org.koin.dsl.module
import org.koin.core.module.dsl.viewModelOf
import com.contraomnese.coffee.login.presentation.LoginViewModel

internal val loginModule = module {

    viewModelOf(::LoginViewModel)
}