package com.contraomnese.coffee.login.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
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
import com.contraomnese.coffee.design.theme.paddingZero
import com.contraomnese.coffee.design.theme.space24
import com.contraomnese.coffee.domain.auth.model.Email
import com.contraomnese.coffee.domain.auth.model.Password

@Composable
internal fun LoginRoute(
    viewModel: LoginViewModel,
    onNavigateToRegister: () -> Unit,
    setTopBar: (TopBarState) -> Unit,
    modifier: Modifier = Modifier,
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        setTopBar(
            TopBarState(
                title = context.getString(R.string.login_title)
            )
        )
    }

    LoginScreen(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        onRegisterClicked = onNavigateToRegister,
        modifier = modifier
    )
}

@Composable
internal fun LoginScreen(
    uiState: LoginUiState,
    onEvent: (LoginEvent) -> Unit,
    onRegisterClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {

    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier
            .padding(top = padding190, start = padding18, end = padding18)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(space24),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextFieldWidget(
            label = stringResource(R.string.email_title),
            value = uiState.login.value,
            onValueChanged = { onEvent(LoginEvent.LoginChanged(it)) },
            isError = !uiState.login.isValidEmail(),
            placeholder = "mail@mail.mail",
        )
        PasswordFormTextField(
            modifier = Modifier.fillMaxWidth(),
            value = uiState.password.value,
            label = stringResource(R.string.password_title),
            onValueChanged = { onEvent(LoginEvent.PasswordChanged(it)) },
        )
        DefaultButton(
            modifier = Modifier.padding(top = padding6),
            title = stringResource(R.string.login_title),
            onClicked = {
                keyboardController?.hide()
                onEvent(LoginEvent.LoginClicked)
            }
        )
        TextButton(
            onClick = onRegisterClicked,
            contentPadding = PaddingValues(paddingZero),
        ) {
            Text(
                text = stringResource(R.string.register_nav_button),
                style = MaterialTheme.typography.labelSmall.copy(
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    CoffeeTheme {
        LoginScreen(
            uiState = LoginUiState(
                login = Email(value = "dolores"),
                password = Password(value = "aliquam"),
            ),
            onEvent = {},
            onRegisterClicked = {}
        )
    }
}