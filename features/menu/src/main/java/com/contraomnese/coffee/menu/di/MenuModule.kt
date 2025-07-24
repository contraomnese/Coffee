package com.contraomnese.coffee.menu.di

import com.contraomnese.coffee.menu.presentation.MenuViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

internal val menuModule = module {

    viewModel {params ->

        val id = params.get<Int>()

        MenuViewModel(
            useCaseExecutorProvider = get(),
            notificationMonitor = get(),
            addToOrderUseCase = get(),
            removeFromOrderUseCase = get(),
            observeOrderUseCase = get(),
            getMenuUseCase = get(),
            clearOrderUseCase = get(),
            locationId = id
        )
    }
}