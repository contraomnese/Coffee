package com.contraomnese.coffee.order.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.contraomnese.coffee.core.ui.model.TopBarState
import com.contraomnese.coffee.core.ui.widgets.LoadingIndicator
import com.contraomnese.coffee.core.ui.widgets.OrderCard
import com.contraomnese.coffee.design.R
import com.contraomnese.coffee.design.theme.CoffeeTheme
import com.contraomnese.coffee.design.theme.cornerSize24
import com.contraomnese.coffee.design.theme.defaultButtonHeight
import com.contraomnese.coffee.design.theme.padding16
import com.contraomnese.coffee.design.theme.space24
import com.contraomnese.coffee.design.theme.space6
import com.contraomnese.coffee.design.theme.space8
import com.contraomnese.coffee.domain.menu.model.MenuItem
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

@Composable
internal fun OrderRoute(
    viewModel: OrderViewModel,
    setTopBar: (TopBarState) -> Unit,
    onNavigateToBack: () -> Unit,
    modifier: Modifier = Modifier,
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        setTopBar(
            TopBarState(
                title = context.getString(R.string.order_title),
                showBackButton = true,
                onBackClicked = onNavigateToBack
            )
        )
    }
    when {
        uiState.isLoading -> LoadingIndicator()
        else -> OrderScreen(
            uiState = uiState,
            onEvent = viewModel::onEvent,
            modifier = modifier
        )
    }

}

@Composable
internal fun OrderScreen(
    uiState: OrderUiState,
    onEvent: (OrderEvent) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(padding16)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(space24)
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(space6),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(uiState.products, key = { it.first.id }) { orderItem ->
                OrderCard(
                    title = orderItem.first.title,
                    price = orderItem.first.price,
                    amount = orderItem.second,
                    onIncrement = { onEvent(OrderEvent.AddToOrder(orderItem.first)) },
                    onDecrement = { onEvent(OrderEvent.RemoveFromOrder(orderItem.first.id)) }
                )
            }
            item {
                Spacer(Modifier.height(space8))
            }
        }
        if (uiState.isOrderPrepared) {
            Text(
                text = stringResource(R.string.order_prepared),
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        } else
            FloatingActionButton(
                modifier = modifier
                    .fillMaxWidth()
                    .requiredHeight(defaultButtonHeight),
                onClick = { onEvent(OrderEvent.ClearOrder) },
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(cornerSize24)
            ) {
                Text(
                    text = stringResource(R.string.pay),
                    style = MaterialTheme.typography.labelLarge,
                )
            }
    }
}

@Preview
@Composable
private fun OrderScreenPreview() {
    CoffeeTheme {
        OrderScreen(
            uiState = OrderUiState(
                products = listOf(
                    MenuItem(
                        id = 1,
                        title = "Espresso",
                        imageUrl = "",
                        price = 200
                    ) to 2,
                    MenuItem(
                        id = 2,
                        title = "Cappuccino",
                        imageUrl = "",
                        price = 300
                    ) to 3,
                    MenuItem(
                        id = 3,
                        title = "Chocolate",
                        imageUrl = "",
                        price = 400
                    ) to 1,
                    MenuItem(
                        id = 4,
                        title = "Latte",
                        imageUrl = "",
                        price = 500
                    ) to 2,
                ).toPersistentList()
            ),
        )
    }
}

@Preview
@Composable
private fun OrderPreparedScreenPreview() {
    CoffeeTheme {
        OrderScreen(
            uiState = OrderUiState(
                products = persistentListOf(),
                isOrderPrepared = true
            ),
        )
    }
}