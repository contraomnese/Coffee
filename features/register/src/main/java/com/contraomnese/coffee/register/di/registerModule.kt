package com.contraomnese.coffee.register.di

import org.koin.dsl.module
import org.koin.core.module.dsl.viewModelOf
import com.contraomnese.coffee.register.presentation.RegisterViewModel

internal val registerModule = module {

    viewModelOf(::RegisterViewModel)
}