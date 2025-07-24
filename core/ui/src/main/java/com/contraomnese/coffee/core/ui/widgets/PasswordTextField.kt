package com.contraomnese.coffee.core.ui.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.contraomnese.coffee.core.ui.utils.colorSelectorByFocus
import com.contraomnese.coffee.design.R
import com.contraomnese.coffee.design.icons.CoffeeIcons
import com.contraomnese.coffee.design.theme.CoffeeTheme

@Composable
fun PasswordFormTextField(
    value: String,
    label: String,
    onValueChanged: (String) -> Unit = {},
    hasError: Boolean = false,
    modifier: Modifier = Modifier,
) {
    var isVisible by remember {
        mutableStateOf(false)
    }

    var isFocused by remember {
        mutableStateOf(false)
    }

    val visibilityIcon by remember {
        derivedStateOf {
            if (isVisible) CoffeeIcons.VisibilityOff
            else CoffeeIcons.Visibility
        }
    }
    val visibilityTransformation by remember {
        derivedStateOf {
            if (isVisible) VisualTransformation.None
            else PasswordVisualTransformation()
        }
    }

    TextFieldWidget(
        modifier = modifier,
        value = value,
        label = label,
        isError = hasError,
        onValueChanged = onValueChanged,
        onTextFieldFocusChanged = { isFocused = it },
        visualTransformation = visibilityTransformation,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon =
            {
                Icon(
                    modifier = Modifier.clickable { isVisible = !isVisible },
                    imageVector = visibilityIcon,
                    contentDescription = stringResource(id = R.string.eye_open),
                    tint = colorSelectorByFocus(isFocused, MaterialTheme.colorScheme.onSurface)
                )
            }
    )
}


@Preview
@Composable
fun PreviewPasswordFormTextField() {
    CoffeeTheme {
        PasswordFormTextField(
            modifier = Modifier.fillMaxWidth(),
            value = "secret",
            label = "Password",
            hasError = false,
            onValueChanged = {})
    }
}