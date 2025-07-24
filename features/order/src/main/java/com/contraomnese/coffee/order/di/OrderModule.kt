package com.contraomnese.coffee.order.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import com.contraomnese.coffee.order.presentation.OrderViewModel

internal val orderModule = module {
    viewModelOf(::OrderViewModel)
}