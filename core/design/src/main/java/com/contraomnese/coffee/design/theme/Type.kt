package com.contraomnese.coffee.design.theme


import androidx.annotation.FontRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.contraomnese.coffee.design.DevicePreviews
import com.contraomnese.coffee.design.R


private val sfUiDisplayFamily = FontFamily(
    Font(
        resId = R.font.sf_ui_display_regular, weight = FontWeight.W400
    ),
    Font(
        resId = R.font.sf_ui_display_bold, weight = FontWeight.W700
    ),
)

val coffeeTypography = Typography(
    displayMedium = TextStyle(
        fontSize = 24.sp,
        fontFamily = sfUiDisplayFamily,
        fontWeight = FontWeight.W500,
        textAlign = TextAlign.Center,
        letterSpacing = (-0.005).em
    ),
    titleLarge = TextStyle(
        fontSize = 18.sp,
        fontFamily = sfUiDisplayFamily,
        fontWeight = FontWeight.W700,
        textAlign = TextAlign.Center,
        letterSpacing = (-0.0066).em
    ),
    titleMedium = TextStyle(
        fontSize = 15.sp,
        fontFamily = sfUiDisplayFamily,
        fontWeight = FontWeight.W400,
        letterSpacing = (-0.008).em
    ),
    bodyMedium = TextStyle(
        fontSize = 18.sp,
        fontFamily = sfUiDisplayFamily,
        fontWeight = FontWeight.W700,
        letterSpacing = (-0.0066).em
    ),
    labelLarge = TextStyle(
        fontSize = 18.sp,
        fontFamily = sfUiDisplayFamily,
        fontWeight = FontWeight.W700,
        letterSpacing = (-0.0077).em
    ),
    labelMedium = TextStyle(
        fontSize = 18.sp,
        fontFamily = sfUiDisplayFamily,
        fontWeight = FontWeight.W700,
        letterSpacing = (-0.0077).em
    ),
    labelSmall = TextStyle(
        fontSize = 14.sp,
        fontFamily = sfUiDisplayFamily,
        fontWeight = FontWeight.W400,
        letterSpacing = (-0.0086).em
    )
)