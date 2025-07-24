package com.contraomnese.coffee.di

import com.contraomnese.coffee.domain.auth.usecases.GetAuthTokenUseCase
import com.contraomnese.coffee.domain.auth.usecases.LoginUserUseCase
import com.contraomnese.coffee.domain.auth.usecases.ObserveAuthTokenUseCase
import com.contraomnese.coffee.domain.auth.usecases.RegisterUserUseCase
import com.contraomnese.coffee.domain.auth.usecases.SaveAuthTokenUseCase
import com.contraomnese.coffee.domain.order.usecases.AddToOrderUseCase
import com.contraomnese.coffee.domain.order.usecases.ObserveOrderUseCase
import com.contraomnese.coffee.domain.order.usecases.RemoveFromOrderUseCase
import com.contraomnese.coffee.domain.locations.usecases.GetLocationsUseCase
import com.contraomnese.coffee.domain.menu.usecases.GetMenuUseCase
import com.contraomnese.coffee.domain.order.usecases.ClearOrderUseCase
import org.koin.dsl.module

val domainModule = module {

    factory<RegisterUserUseCase> { RegisterUserUseCase(repository = get(), coroutineContextProvider = get()) }
    factory<LoginUserUseCase> { LoginUserUseCase(repository = get(), coroutineContextProvider = get()) }

    factory<GetAuthTokenUseCase> { GetAuthTokenUseCase(repository = get(), coroutineContextProvider = get()) }
    factory<SaveAuthTokenUseCase> { SaveAuthTokenUseCase(repository = get(), coroutineContextProvider = get()) }
    factory<ObserveAuthTokenUseCase> { ObserveAuthTokenUseCase(repository = get()) }

    factory<GetLocationsUseCase> { GetLocationsUseCase(repository = get(), coroutineContextProvider = get()) }
    factory<GetMenuUseCase> { GetMenuUseCase(repository = get(), coroutineContextProvider = get()) }

    factory<AddToOrderUseCase> { AddToOrderUseCase(repository = get(), coroutineContextProvider = get()) }
    factory<RemoveFromOrderUseCase> { RemoveFromOrderUseCase(repository = get(), coroutineContextProvider = get()) }
    factory<ObserveOrderUseCase> { ObserveOrderUseCase(repository = get()) }
    factory<ClearOrderUseCase> { ClearOrderUseCase(repository = get(), coroutineContextProvider = get()) }

}