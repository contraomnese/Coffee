package com.contraomnese.coffee.domain.menu.usecases

import com.contraomnese.coffee.domain.cleanarchitecture.coroutine.CoroutineContextProvider
import com.contraomnese.coffee.domain.cleanarchitecture.usecase.background.withRequest.BackgroundExecutingUseCaseWithRequest
import com.contraomnese.coffee.domain.menu.model.MenuItem
import com.contraomnese.coffee.domain.menu.repository.MenuRepository

class GetMenuUseCase(
    private val repository: MenuRepository,
    private val coroutineContextProvider: CoroutineContextProvider
): BackgroundExecutingUseCaseWithRequest<Int, List<MenuItem>>(coroutineContextProvider) {

    override suspend fun executeInBackground(request: Int): List<MenuItem> = repository.getMenu(request)
}