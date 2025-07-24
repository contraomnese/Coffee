package com.contraomnese.coffee.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import com.contraomnese.coffee.MainActivityViewModel
import com.contraomnese.coffee.core.ui.utils.LocationProvider

val appModule = module {

    viewModelOf(::MainActivityViewModel)
    single<LocationProvider> { LocationProvider(context = get()) }

}