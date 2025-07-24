package com.contraomnese.coffee.core.ui.widgets

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.contraomnese.coffee.core.ui.utils.colorSelectorByFocus
import com.contraomnese.coffee.design.icons.CoffeeIcons
import com.contraomnese.coffee.design.theme.CoffeeTheme
import com.contraomnese.coffee.design.theme.cornerRadius24
import com.contraomnese.coffee.design.theme.itemHeight48
import com.contraomnese.coffee.design.theme.padding12
import com.contraomnese.coffee.design.theme.padding20
import com.contraomnese.coffee.design.theme.space8
import com.contraomnese.coffee.design.theme.thickness1
import com.contraomnese.coffee.design.theme.thickness2

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldWidget(
    label: String,
    value: String,
    onValueChanged: (String) -> Unit,
    onTextFieldFocusChanged: (Boolean) -> Unit = {},
    isError: Boolean = false,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    placeholder: String = "",
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Go),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    modifier: Modifier = Modifier,
) {

    val focusRequester = remember { FocusRequester() }
    val currentKeyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val containerColor: Color = MaterialTheme.colorScheme.background
    val innerContainerColor: Color = MaterialTheme.colorScheme.onSurfaceVariant
    val outlineColor: Color = MaterialTheme.colorScheme.onSurface

    LaunchedEffect(isFocused) {
        onTextFieldFocusChanged(isFocused)
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(space8)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleMedium,
            color = outlineColor,
            textAlign = TextAlign.Start
        )
        BasicTextField(
            value = value,
            onValueChange = { newValue ->
                onValueChanged(newValue)
            },
            modifier = modifier
                .height(itemHeight48)
                .focusRequester(focusRequester)
                .fillMaxWidth(),
            singleLine = singleLine,
            maxLines = 1,
            textStyle = MaterialTheme.typography.labelMedium.copy(
                color = colorSelectorByFocus(isFocused, innerContainerColor)
            ),
            keyboardOptions = keyboardOptions,
            keyboardActions = KeyboardActions(
                onGo = {
                    focusManager.clearFocus(force = true)
                    currentKeyboardController?.hide()
                }
            ),
            interactionSource = interactionSource,
            visualTransformation = visualTransformation,
            cursorBrush = SolidColor(outlineColor),
            decorationBox = { innerTextField ->
                OutlinedTextFieldDefaults.DecorationBox(
                    value = value,
                    innerTextField = innerTextField,
                    enabled = enabled,
                    isError = isError,
                    singleLine = singleLine,
                    leadingIcon = leadingIcon,
                    trailingIcon = trailingIcon ?: {
                        if (value.isNotEmpty()) {
                            IconButton(
                                onClick = {
                                    onValueChanged("")
                                },
                            ) {
                                Icon(
                                    imageVector = CoffeeIcons.Close,
                                    contentDescription = null,
                                    tint = colorSelectorByFocus(isFocused, innerContainerColor)
                                )
                            }
                        }
                    },
                    placeholder = {
                        Text(
                            text = placeholder,
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.W400),
                            textAlign = TextAlign.Start
                        )
                    },
                    visualTransformation = visualTransformation,
                    interactionSource = interactionSource,
                    contentPadding = PaddingValues(horizontal = padding20, vertical = padding12),
                    container = {
                        OutlinedTextFieldDefaults.Container(
                            enabled = enabled,
                            isError = isError,
                            interactionSource = interactionSource,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = colorSelectorByFocus(isFocused, containerColor),
                                unfocusedContainerColor = colorSelectorByFocus(isFocused, containerColor),
                                focusedBorderColor = colorSelectorByFocus(isFocused, outlineColor),
                                unfocusedBorderColor = colorSelectorByFocus(isFocused, outlineColor),
                            ),
                            shape = RoundedCornerShape(cornerRadius24),
                            focusedBorderThickness = thickness2,
                            unfocusedBorderThickness = thickness1,
                        )
                    }
                )
            }
        )
    }
}

@Preview
@Composable
private fun TextFieldPlaceholderPreview() {
    CoffeeTheme {
        TextFieldWidget(
            label = "title",
            value = "",
            onValueChanged = {},
            onTextFieldFocusChanged = {},
            isError = false,
            singleLine = true,
            placeholder = "placeholder",
        )
    }
}

@Preview
@Composable
private fun TextFieldValuePreview() {
    CoffeeTheme {
        TextFieldWidget(
            label = "title",
            value = "value",
            onValueChanged = {},
            onTextFieldFocusChanged = {},
            isError = false,
            singleLine = true,
            placeholder = "placeholder",
        )
    }
}

@Preview
@Composable
private fun TextFieldIsErrorPreview() {
    CoffeeTheme {
        TextFieldWidget(
            label = "title",
            value = "value",
            onValueChanged = {},
            onTextFieldFocusChanged = {},
            isError = true,
            singleLine = true,
            placeholder = "placeholder",
        )
    }
}