package com.contraomnese.coffee.di

import com.contraomnese.coffee.domain.cleanarchitecture.coroutine.CoroutineContextProvider
import com.contraomnese.coffee.domain.cleanarchitecture.usecase.UseCaseExecutor
import com.contraomnese.coffee.presentation.coroutine.AppCoroutineContextProvider
import com.contraomnese.coffee.presentation.notification.NotificationMonitor
import com.contraomnese.coffee.presentation.usecase.UseCaseExecutorProvider
import org.koin.dsl.module

val architecturePresentationModule = module {

    single<CoroutineContextProvider> {
        AppCoroutineContextProvider()
    }
    single<UseCaseExecutorProvider> {
        ::UseCaseExecutor
    }
    single<NotificationMonitor> { NotificationMonitor() }
}