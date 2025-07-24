package com.contraomnese.coffee.presentation.coroutine

import com.contraomnese.coffee.domain.cleanarchitecture.coroutine.CoroutineContextProvider
import kotlinx.coroutines.Dispatchers

class AppCoroutineContextProvider : CoroutineContextProvider {
    override val main = Dispatchers.Main
    override val io = Dispatchers.IO
}