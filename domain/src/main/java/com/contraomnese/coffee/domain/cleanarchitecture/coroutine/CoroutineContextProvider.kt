package com.contraomnese.coffee.domain.cleanarchitecture.coroutine

import kotlin.coroutines.CoroutineContext

interface CoroutineContextProvider {
    val main: CoroutineContext
    val io: CoroutineContext
}