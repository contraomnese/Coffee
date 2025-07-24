package com.contraomnese.coffee.presentation.architecture

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.contraomnese.coffee.domain.cleanarchitecture.exception.DomainException
import com.contraomnese.coffee.domain.cleanarchitecture.exception.LoginAlreadyUsedException
import com.contraomnese.coffee.domain.cleanarchitecture.exception.RequestBodyException
import com.contraomnese.coffee.domain.cleanarchitecture.exception.UserNotAuthorizedException
import com.contraomnese.coffee.domain.cleanarchitecture.exception.UserNotExistException
import com.contraomnese.coffee.domain.cleanarchitecture.usecase.StreamingUseCase
import com.contraomnese.coffee.domain.cleanarchitecture.usecase.UseCase
import com.contraomnese.coffee.domain.cleanarchitecture.usecase.UseCaseWithRequest
import com.contraomnese.coffee.design.R
import com.contraomnese.coffee.presentation.notification.NotificationMonitor
import com.contraomnese.coffee.presentation.usecase.UseCaseExecutorProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<ViewState : UiState, Event: Any>(
    private val useCaseExecutorProvider: UseCaseExecutorProvider,
    private val notificationMonitor: NotificationMonitor
) : ViewModel() {

    protected abstract fun initialState(): ViewState

    private val _uiState = MutableStateFlow(this.initialState())
    val uiState: StateFlow<ViewState> = _uiState.asStateFlow()

    private val useCaseExecutor by lazy {
        useCaseExecutorProvider(viewModelScope)
    }

    protected fun <OUTPUT> execute(
        useCase: UseCase<OUTPUT>,
        onSuccess: (OUTPUT) -> Unit = {},
        onException: (DomainException) -> Unit = {}
    ) {
        useCaseExecutor.execute(useCase, onSuccess, onException)
    }

    protected fun <INPUT, OUTPUT> execute(
        useCaseWithRequest: UseCaseWithRequest<INPUT, OUTPUT>,
        value: INPUT,
        onSuccess: (OUTPUT) -> Unit = {},
        onException: (DomainException) -> Unit = {}
    ) {
        useCaseExecutor.execute(useCaseWithRequest, value, onSuccess, onException)
    }

    protected fun <OUTPUT> observe(
        useCase: StreamingUseCase<OUTPUT>,
        onEach: (OUTPUT) -> Unit,
        onException: (DomainException) -> Unit = {}
    ) {
        useCaseExecutor.observe(useCase, onEach, onException)
    }

    abstract fun onEvent(event: Event)

    private fun updateViewState(newViewState: ViewState) {
        _uiState.update {
            newViewState
        }
    }

    protected fun updateViewState(
        updatedState: ViewState.() -> ViewState
    ) = updateViewState(_uiState.value.updatedState())

    protected fun observeNotificationEvents(): Flow<Int> = notificationMonitor.notifications

    protected fun showNotification(@StringRes notification: Int) {
        viewModelScope.launch {
            notificationMonitor.emit(notification)
        }
    }

    protected fun provideException(exception: DomainException) {
        when (exception) {
            is RequestBodyException -> showNotification(R.string.request_body_exception)
            is UserNotAuthorizedException -> showNotification(R.string.user_not_authorized_exception)
            is UserNotExistException -> showNotification(R.string.user_not_exist_exception)
            is LoginAlreadyUsedException -> showNotification(R.string.login_already_used_exception)
            else -> showNotification(R.string.unknown_domain_exception)
        }
    }
}