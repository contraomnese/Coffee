package com.contraomnese.coffee.register.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.contraomnese.coffee.core.ui.model.TopBarState
import com.contraomnese.coffee.core.ui.widgets.DefaultButton
import com.contraomnese.coffee.core.ui.widgets.PasswordFormTextField
import com.contraomnese.coffee.core.ui.widgets.TextFieldWidget
import com.contraomnese.coffee.design.R
import com.contraomnese.coffee.design.theme.CoffeeTheme
import com.contraomnese.coffee.design.theme.padding18
import com.contraomnese.coffee.design.theme.padding190
import com.contraomnese.coffee.design.theme.padding6
import com.contraomnese.coffee.design.theme.space24
import com.contraomnese.coffee.domain.auth.model.Email
import com.contraomnese.coffee.domain.auth.model.Password

@Composable
internal fun RegisterRoute(
    viewModel: RegisterViewModel,
    setTopBar: (TopBarState) -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        setTopBar(
            TopBarState(
                title = context.getString(R.string.register_title),
                showBackButton = true,
                onBackClicked = onNavigateBack
            )
        )
    }

    RegisterScreen(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        modifier = modifier
    )
}

@Composable
internal fun RegisterScreen(
    uiState: RegisterUiState,
    onEvent: (RegisterEvent) -> Unit,
    modifier: Modifier = Modifier,
) {

    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier
            .padding(top = padding190, start = padding18, end = padding18)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(space24)
    ) {
        TextFieldWidget(
            label = stringResource(R.string.email_title),
            value = uiState.login.value,
            onValueChanged = { onEvent(RegisterEvent.LoginChanged(it)) },
            isError = !uiState.login.isValidEmail(),
            placeholder = "mail@mail.mail",
        )
        PasswordFormTextField(
            modifier = Modifier.fillMaxWidth(),
            value = uiState.password.value,
            label = stringResource(R.string.password_title),
            onValueChanged = { onEvent(RegisterEvent.PasswordChanged(it)) }
        )
        PasswordFormTextField(
            modifier = Modifier.fillMaxWidth(),
            value = uiState.confirmPassword.value,
            hasError = !uiState.isPasswordMatch(),
            label = stringResource(R.string.confirm_password_title),
            onValueChanged = { onEvent(RegisterEvent.ConfirmPasswordChanged(it)) },
        )
        DefaultButton(
            modifier = Modifier.padding(top = padding6),
            title = stringResource(R.string.register_title),
            onClicked = {
                keyboardController?.hide()
                onEvent(RegisterEvent.RegisterClicked)
            }
        )
    }
}

@Preview
@Composable
private fun RegisterScreenPreview() {
    CoffeeTheme {
        RegisterScreen(
            uiState = RegisterUiState(
                login = Email(value = "dolores"),
                password = Password(value = "aliquam"),
                confirmPassword = Password(value = "aliquam")
            ),
            onEvent = {},
        )
    }
}