package com.contraomnese.coffee.map.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import com.contraomnese.coffee.map.presentation.MapViewModel

internal val mapModule = module {
    viewModelOf(::MapViewModel)
}