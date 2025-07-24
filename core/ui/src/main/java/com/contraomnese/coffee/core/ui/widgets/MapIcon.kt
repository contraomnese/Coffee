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
fun MapIcon(
    onClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    IconButton(
        onClick = onClicked,
        modifier = modifier
    ) {
        Icon(
            imageVector = CoffeeIcons.Map,
            contentDescription = stringResource(id = R.string.open_map_description),
        )
    }
}

@Preview
@Composable
private fun MapIconPreview() {
    CoffeeTheme {
        MapIcon(onClicked = {})
    }
}