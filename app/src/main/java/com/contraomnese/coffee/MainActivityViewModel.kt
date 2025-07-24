package com.contraomnese.coffee

import androidx.compose.runtime.Immutable
import com.contraomnese.coffee.core.ui.model.TopBarState
import com.contraomnese.coffee.presentation.notification.NotificationMonitor
import com.contraomnese.coffee.domain.auth.model.Token
import com.contraomnese.coffee.domain.auth.usecases.GetAuthTokenUseCase
import com.contraomnese.coffee.domain.auth.usecases.ObserveAuthTokenUseCase
import com.contraomnese.coffee.domain.cleanarchitecture.exception.DomainException
import com.contraomnese.coffee.presentation.architecture.BaseViewModel
import com.contraomnese.coffee.presentation.architecture.UiState
import com.contraomnese.coffee.presentation.usecase.UseCaseExecutorProvider
import kotlinx.coroutines.flow.Flow

@Immutable
internal sealed class MainActivityEvent {
    data class SetTopBar(val state: TopBarState) : MainActivityEvent()
}

internal data class MainActivityUiState(
    override val isLoading: Boolean = false,
    val isAuth: Boolean = false,
    val topBarState: TopBarState? = null,
) : UiState {

    override fun loading(): UiState = copy(isLoading = true)
    fun shouldSkipAuth(): Boolean = isAuth
}

internal class MainActivityViewModel(
    private val notificationMonitor: NotificationMonitor,
    private val useCaseExecutorProvider: UseCaseExecutorProvider,
    private val getAuthTokenUseCase: GetAuthTokenUseCase,
    private val observeAuthTokenUseCase: ObserveAuthTokenUseCase,
) : BaseViewModel<MainActivityUiState, MainActivityEvent>(useCaseExecutorProvider, notificationMonitor) {

    override fun initialState(): MainActivityUiState = MainActivityUiState(isLoading = true)

    val notificationEvents: Flow<Int> = observeNotificationEvents()

    init {
        execute(
            getAuthTokenUseCase,
            onSuccess = ::checkTokenExpiry,
            onException = ::tokenNotFounded
        )
        observe(observeAuthTokenUseCase, ::observeToken, ::provideException)
    }

    override fun onEvent(event: MainActivityEvent) {
        when (event) {
            is MainActivityEvent.SetTopBar -> setTopBar(event.state)
        }
    }

    private fun setTopBar(state: TopBarState) {
        updateViewState { copy(topBarState = state) }
    }

    private fun checkTokenExpiry(token: Token) {
        val isAuth = token.lifeTime > System.currentTimeMillis()
        updateViewState { copy(isAuth = isAuth, isLoading = false) }
    }

    private fun tokenNotFounded(exception: DomainException) {
        updateViewState { copy(isAuth = false, isLoading = false) }
    }

    private fun observeToken(token: Token?) {
        token?.let { updateViewState { copy(isAuth = true) } }
    }
}

