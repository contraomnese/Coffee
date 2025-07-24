package com.contraomnese.coffee.core.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun colorSelectorByFocus(isFocused: Boolean, color: Color) = if (isFocused) color else color.copy(alpha = 0.8f)