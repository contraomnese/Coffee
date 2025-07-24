package com.contraomnese.coffee.menu.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.contraomnese.coffee.core.ui.model.TopBarState
import com.contraomnese.coffee.core.ui.widgets.CoffeeCard
import com.contraomnese.coffee.design.R
import com.contraomnese.coffee.design.theme.CoffeeTheme
import com.contraomnese.coffee.design.theme.cornerSize24
import com.contraomnese.coffee.design.theme.defaultButtonHeight
import com.contraomnese.coffee.design.theme.padding16
import com.contraomnese.coffee.design.theme.space24
import com.contraomnese.coffee.design.theme.space8
import com.contraomnese.coffee.domain.menu.model.MenuItem
import kotlinx.collections.immutable.toPersistentList

@Composable
internal fun MenuRoute(
    viewModel: MenuViewModel,
    setTopBar: (TopBarState) -> Unit,
    onNavigateToBack: () -> Unit,
    onNavigateToOrder: () -> Unit,
    modifier: Modifier = Modifier,
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        setTopBar(
            TopBarState(
                title = context.getString(R.string.menu_title),
                showBackButton = true,
                onBackClicked = {
                    viewModel.onEvent(MenuEvent.ClearOrder)
                    onNavigateToBack()
                }
            )
        )
    }

    MenuScreen(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        onProceedToPaymentClicked = onNavigateToOrder,
        modifier = modifier
    )
}

@Composable
internal fun MenuScreen(
    uiState: MenuUiState,
    onEvent: (MenuEvent) -> Unit,
    onProceedToPaymentClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(padding16)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(space24)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(13.dp),
            horizontalArrangement = Arrangement.spacedBy(13.dp),
            contentPadding = PaddingValues(bottom = space8)
        ) {
            items(uiState.products) { menuItem ->
                CoffeeCard(
                    imageUrl = menuItem.first.imageUrl,
                    title = menuItem.first.title,
                    price = menuItem.first.price,
                    amount = menuItem.second,
                    onIncrement = { onEvent(MenuEvent.AddToOrder(menuItem.first)) },
                    onDecrement = { onEvent(MenuEvent.RemoveFromOrder(menuItem.first.id)) }
                )
            }
        }
        FloatingActionButton(
            modifier = modifier
                .fillMaxWidth()
                .requiredHeight(defaultButtonHeight),
            onClick = onProceedToPaymentClicked,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            shape = RoundedCornerShape(cornerSize24)
        ) {
            Text(
                text = stringResource(R.string.proceed_to_payment),
                style = MaterialTheme.typography.labelLarge,
            )
        }
    }
}

@Preview
@Composable
private fun MenuScreenPreview() {
    CoffeeTheme {
        MenuScreen(
            uiState = MenuUiState(
                products = listOf(
                    MenuItem(
                        id = 1,
                        title = "Espresso",
                        imageUrl = "",
                        price = 200
                    ) to 0,
                    MenuItem(
                        id = 2,
                        title = "Cappuccino",
                        imageUrl = "",
                        price = 300
                    ) to 0,
                    MenuItem(
                        id = 3,
                        title = "Chocolate",
                        imageUrl = "",
                        price = 400
                    ) to 0,
                    MenuItem(
                        id = 4,
                        title = "Latte",
                        imageUrl = "",
                        price = 500
                    ) to 0,
                ).toPersistentList()
            ),
            onEvent = {},
            onProceedToPaymentClicked = {},
        )
    }
}