package com.contraomnese.coffee.register.presentation

import androidx.compose.runtime.Immutable
import com.contraomnese.coffee.design.R
import com.contraomnese.coffee.domain.auth.model.Email
import com.contraomnese.coffee.domain.auth.model.Password
import com.contraomnese.coffee.domain.auth.model.Token
import com.contraomnese.coffee.domain.auth.model.UserParams
import com.contraomnese.coffee.domain.auth.usecases.RegisterUserUseCase
import com.contraomnese.coffee.domain.auth.usecases.SaveAuthTokenUseCase
import com.contraomnese.coffee.presentation.architecture.BaseViewModel
import com.contraomnese.coffee.presentation.architecture.UiState
import com.contraomnese.coffee.presentation.notification.NotificationMonitor
import com.contraomnese.coffee.presentation.usecase.UseCaseExecutorProvider

@Immutable
internal data class RegisterUiState(
    override val isLoading: Boolean = false,
    val login: Email = Email(""),
    val password: Password = Password(""),
    val confirmPassword: Password = Password("")
): UiState {

    override fun loading() = copy(isLoading = true)
    fun isPasswordMatch() = confirmPassword.value == password.value
    fun isValid() = isPasswordMatch() && login.isValidEmail() && login.value.isNotEmpty() && password.value.isNotEmpty()
}

@Immutable
internal sealed interface RegisterEvent {
    data object RegisterClicked : RegisterEvent
    data class LoginChanged(val newLogin: String) : RegisterEvent
    data class PasswordChanged(val newPassword: String) : RegisterEvent
    data class ConfirmPasswordChanged(val newConfirmPassword: String) : RegisterEvent
}

internal class RegisterViewModel(
    private val notificationMonitor: NotificationMonitor,
    private val useCaseExecutorProvider: UseCaseExecutorProvider,
    private val registerUserUseCase: RegisterUserUseCase,
    private val saveAuthTokenUseCase: SaveAuthTokenUseCase
): BaseViewModel<RegisterUiState, RegisterEvent>(useCaseExecutorProvider, notificationMonitor) {

    override fun initialState(): RegisterUiState = RegisterUiState()

    override fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.LoginChanged -> onLoginChanged(event.newLogin)
            is RegisterEvent.PasswordChanged -> onPasswordChanged(event.newPassword)
            is RegisterEvent.ConfirmPasswordChanged -> onConfirmPasswordChanged(event.newConfirmPassword)
            is RegisterEvent.RegisterClicked -> onRegister()
        }
    }

    private fun onLoginChanged(newLogin: String) {
        updateViewState { copy(login = Email(newLogin)) }
    }

    private fun onPasswordChanged(newPassword: String) {
        updateViewState { copy(password = Password(newPassword)) }
    }

    private fun onConfirmPasswordChanged(newConfirmPassword: String) {
        updateViewState { copy(confirmPassword = Password(newConfirmPassword)) }
    }

    private fun onRegister() {
        if (uiState.value.isValid()) {
            execute(registerUserUseCase, UserParams(login = uiState.value.login, password = uiState.value.password), ::saveToken, ::provideException)
        } else {
            showNotification(notification = R.string.wrong_input_data)
        }
    }

    private fun saveToken(token: Token) {
        execute(saveAuthTokenUseCase, token, onException = ::provideException)
    }

}