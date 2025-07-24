package com.contraomnese.coffee.login.presentation

import android.util.Log
import androidx.compose.runtime.Immutable
import com.contraomnese.coffee.presentation.notification.NotificationMonitor
import com.contraomnese.coffee.domain.auth.model.Email
import com.contraomnese.coffee.domain.auth.model.Password
import com.contraomnese.coffee.design.R
import com.contraomnese.coffee.domain.auth.model.Token
import com.contraomnese.coffee.domain.auth.model.UserParams
import com.contraomnese.coffee.domain.auth.usecases.LoginUserUseCase
import com.contraomnese.coffee.domain.auth.usecases.SaveAuthTokenUseCase
import com.contraomnese.coffee.presentation.architecture.BaseViewModel
import com.contraomnese.coffee.presentation.architecture.UiState
import com.contraomnese.coffee.presentation.usecase.UseCaseExecutorProvider

@Immutable
internal data class LoginUiState(
    override val isLoading: Boolean = false,
    val login: Email = Email(""),
    val password: Password = Password(""),
) : UiState {
    override fun loading(): UiState = copy(isLoading = true)
    fun isValid() = login.isValidEmail() && login.value.isNotEmpty() && password.value.isNotEmpty()
}

@Immutable
internal sealed interface LoginEvent {
    data object LoginClicked : LoginEvent
    data class LoginChanged(val newLogin: String) : LoginEvent
    data class PasswordChanged(val newPassword: String) : LoginEvent
}

internal class LoginViewModel(
    private val notificationMonitor: NotificationMonitor,
    private val useCaseExecutorProvider: UseCaseExecutorProvider,
    private val loginUserUseCase: LoginUserUseCase,
    private val saveAuthTokenUseCase: SaveAuthTokenUseCase
) : BaseViewModel<LoginUiState, LoginEvent>(useCaseExecutorProvider, notificationMonitor) {

    override fun initialState(): LoginUiState = LoginUiState()

    override fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.LoginChanged -> onLoginChanged(event.newLogin)
            is LoginEvent.PasswordChanged -> onPasswordChanged(event.newPassword)
            is LoginEvent.LoginClicked -> onLogin()
        }
    }

    private fun onLoginChanged(newLogin: String) {
        updateViewState { copy(login = Email(newLogin)) }
    }

    private fun onPasswordChanged(newPassword: String) {
        updateViewState { copy(password = Password(newPassword)) }
    }

    private fun onLogin() {
        if (uiState.value.isValid()) {
            execute(loginUserUseCase, UserParams(login = uiState.value.login, password = uiState.value.password), ::saveToken, ::provideException)
        } else {
            showNotification(notification = R.string.wrong_input_data)
        }
    }

    private fun saveToken(token: Token) {
        execute(saveAuthTokenUseCase, token, onException = ::provideException)
    }
}