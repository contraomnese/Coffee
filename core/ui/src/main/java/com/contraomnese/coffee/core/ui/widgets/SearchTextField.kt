package com.contraomnese.coffee.core.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import com.contraomnese.coffee.design.DevicePreviews
import com.contraomnese.coffee.design.icons.CoffeeIcons
import com.contraomnese.coffee.design.theme.CoffeeTheme
import com.contraomnese.coffee.design.theme.cornerRadius8
import com.contraomnese.coffee.design.theme.itemHeight40
import com.contraomnese.coffee.design.theme.padding16

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTextField(
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    onSearchTriggered: (String) -> Unit,
    onSearchFocusChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    LaunchedEffect(isFocused) {
        onSearchFocusChanged(isFocused)
    }

    Box(
        modifier = modifier
            .height(itemHeight40)
            .background(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                shape = RoundedCornerShape(cornerRadius8)
            ),
        contentAlignment = Alignment.CenterStart
    ) {
        BasicTextField(
            value = searchQuery,
            onValueChange = { newValue ->
                onSearchQueryChanged(newValue)
            },
            modifier = Modifier
                .focusRequester(focusRequester)
                .fillMaxWidth(),
            singleLine = true,
            maxLines = 1,
            textStyle = MaterialTheme.typography.labelMedium.copy(
                color = colorSelectorByFocus(isFocused)
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Go,
                keyboardType = KeyboardType.Number
            ),
            keyboardActions = KeyboardActions(
                onGo = {
                    focusManager.clearFocus(force = true)
                    keyboardController?.hide()
                }
            ),
            interactionSource = interactionSource,
            cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
            decorationBox = { innerTextField ->
                TextFieldDefaults.DecorationBox(
                    value = searchQuery,
                    innerTextField = innerTextField,
                    enabled = true,
                    singleLine = true,
                    leadingIcon = {
                        Icon(
                            imageVector = CoffeeIcons.Search,
                            contentDescription = null,
                            tint = colorSelectorByFocus(isFocused)
                        )
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(
                                onClick = {
                                    onSearchQueryChanged("")
                                },
                            ) {
                                Icon(
                                    imageVector = CoffeeIcons.Close,
                                    contentDescription = null,
                                    tint = colorSelectorByFocus(isFocused)
                                )
                            }
                        }
                    },
                    placeholder = {
                        Text(
                            text = "Search city",
                            style = MaterialTheme.typography.labelMedium,
                            textAlign = TextAlign.Start
                        )
                    },
                    visualTransformation = VisualTransformation.None,
                    interactionSource = interactionSource,
                    contentPadding = PaddingValues(horizontal = padding16),
                    container = {
                        TextFieldDefaults.Container(
                            enabled = true,
                            isError = false,
                            interactionSource = interactionSource,
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = MaterialTheme.colorScheme.onSurface.copy(0.25f),
                                unfocusedContainerColor = MaterialTheme.colorScheme.onSurface.copy(0.1f),
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                errorIndicatorColor = Color.Transparent
                            ),
                            shape = RoundedCornerShape(cornerRadius8),
                        )
                    }
                )
            }
        )
    }
}

@Composable
private fun colorSelectorByFocus(isFocused: Boolean) = if (isFocused) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f) else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)

@DevicePreviews
@Composable
private fun SearchTextFieldPreview() {
    CoffeeTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            SearchTextField(
                onSearchTriggered = {},
                searchQuery = "empty",
                onSearchQueryChanged = {},
                onSearchFocusChanged = {},
                modifier = Modifier.padding(padding16)
            )
        }
    }
}