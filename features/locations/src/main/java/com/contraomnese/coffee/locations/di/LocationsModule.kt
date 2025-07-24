package com.contraomnese.coffee.locations.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import com.contraomnese.coffee.locations.presentation.LocationsViewModel

internal val locationsModule = module {
    viewModelOf(::LocationsViewModel)
}