package com.contraomnese.coffee.core.ui.widgets

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.contraomnese.coffee.design.theme.CoffeeTheme
import com.contraomnese.coffee.design.theme.defaultButtonHeight
import com.contraomnese.coffee.design.theme.cornerSize24
import com.contraomnese.coffee.design.theme.padding12

@Composable
fun DefaultButton(
    modifier: Modifier = Modifier,
    title: String,
    onClicked: () -> Unit
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .requiredHeight(defaultButtonHeight),
        onClick = onClicked,
        contentPadding = PaddingValues(vertical = padding12),
        colors = ButtonColors(
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            disabledContentColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.1f),
            disabledContainerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.1f)
        ),
        shape = RoundedCornerShape(cornerSize24)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelLarge,
        )
    }
}

@Preview
@Composable
private fun DefaultButtonPreview() {
    CoffeeTheme {
        DefaultButton(
            title = "Title",
            onClicked = { }
        )
    }
}