package com.contraomnese.coffee.core.ui.widgets

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.contraomnese.coffee.design.R
import com.contraomnese.coffee.design.icons.CoffeeIcons
import com.contraomnese.coffee.design.theme.CoffeeTheme

@Composable
fun NavigationBackIcon(
    onBackClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    IconButton(
        onClick = { onBackClicked() },
        modifier = modifier
    ) {
        Icon(
            imageVector = CoffeeIcons.ArrowBack,
            contentDescription = stringResource(id = R.string.back_arrow_content_description),
        )
    }
}

@Preview
@Composable
private fun NavigationIconPreview() {
    CoffeeTheme {
        NavigationBackIcon(onBackClicked = {})
    }
}