package com.contraomnese.coffee.presentation.usecase

import com.contraomnese.coffee.domain.cleanarchitecture.usecase.UseCaseExecutor
import kotlinx.coroutines.CoroutineScope

typealias UseCaseExecutorProvider =
        @JvmSuppressWildcards (coroutineScope: CoroutineScope) -> UseCaseExecutor