package com.contraomnese.coffee.core.ui.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.contraomnese.coffee.design.R
import com.contraomnese.coffee.design.icons.CoffeeIcons
import com.contraomnese.coffee.design.theme.CoffeeTheme
import com.contraomnese.coffee.design.theme.cornerSize5
import com.contraomnese.coffee.design.theme.elevation4
import com.contraomnese.coffee.design.theme.itemHeight71
import com.contraomnese.coffee.design.theme.itemWidth24
import com.contraomnese.coffee.design.theme.padding10
import com.contraomnese.coffee.design.theme.padding14
import com.contraomnese.coffee.design.theme.padding9
import com.contraomnese.coffee.design.theme.space6


@Composable
fun OrderCard(
    title: String,
    price: Int,
    amount: Int,
    onIncrement: () -> Unit = {},
    onDecrement: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Card(
        shape = RoundedCornerShape(cornerSize5),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = elevation4),
        modifier = modifier
            .fillMaxWidth()
            .height(itemHeight71)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = padding14, start = padding10, end = padding10),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier.wrapContentWidth()
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(space6))
                Text(
                    text = "$price ".plus(stringResource(R.string.rub)),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            AddRemoveBlock(
                amount = amount, onIncrement = onIncrement, onDecrement = onDecrement
            )
        }
    }
}

@Composable
private fun AddRemoveBlock(
    amount: Int,
    onIncrement: () -> Unit = {},
    onDecrement: () -> Unit = {},
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(padding9)
        ) {
            IconButton(
                modifier = Modifier.size(itemWidth24),
                onClick = onDecrement
            ) {
                Icon(
                    CoffeeIcons.Remove,
                    contentDescription = stringResource(R.string.decrease_button),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                text = amount.toString(),
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.W700
                ),
                color = MaterialTheme.colorScheme.onSurface
            )
            IconButton(
                modifier = Modifier.size(itemWidth24),
                onClick = onIncrement
            ) {
                Icon(
                    CoffeeIcons.Add,
                    contentDescription = stringResource(R.string.increase_button),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Preview
@Composable
private fun LocationCardPreview() {
    CoffeeTheme {
        OrderCard(
            title = "Espresso",
            price = 200,
            amount = 3,
        )
    }
}