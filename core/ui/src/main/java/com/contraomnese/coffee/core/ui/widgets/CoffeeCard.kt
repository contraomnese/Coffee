package com.contraomnese.coffee.core.ui.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.contraomnese.coffee.core.ui.utils.CoffeePlaceholder
import com.contraomnese.coffee.core.ui.utils.getPlaceholderByWidth
import com.contraomnese.coffee.design.R
import com.contraomnese.coffee.design.icons.CoffeeIcons
import com.contraomnese.coffee.design.theme.CoffeeTheme
import com.contraomnese.coffee.design.theme.cornerSize5
import com.contraomnese.coffee.design.theme.elevation4
import com.contraomnese.coffee.design.theme.itemHeight137
import com.contraomnese.coffee.design.theme.itemHeight205
import com.contraomnese.coffee.design.theme.itemWidth165
import com.contraomnese.coffee.design.theme.itemWidth24
import com.contraomnese.coffee.design.theme.itemWidth70
import com.contraomnese.coffee.design.theme.padding10
import com.contraomnese.coffee.design.theme.padding11
import com.contraomnese.coffee.design.theme.padding12
import com.contraomnese.coffee.design.theme.padding9


@Composable
fun CoffeeCard(
    imageUrl: String,
    title: String,
    price: Int,
    amount: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp

    val placeholderResId = remember { getPlaceholderByWidth(CoffeePlaceholder.getRandomPlaceholder(), screenWidth)}

    Card(
        shape = RoundedCornerShape(cornerSize5),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = elevation4),
        modifier = Modifier
            .width(itemWidth165)
            .height(itemHeight205)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(imageUrl)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(id = placeholderResId),
                error = painterResource(id = placeholderResId),
                contentDescription = title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(itemHeight137)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = padding10, start = padding11, end = padding11)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth().padding(top = padding12)
                ) {
                    Text(
                        modifier = Modifier.width(itemWidth70),
                        text = "$price ".plus(stringResource(R.string.rub)),
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.W700
                        ),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    if (amount >= 1) {
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
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Text(
                                modifier = Modifier.align(Alignment.CenterVertically),
                                text = amount.toString(),
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            IconButton(
                                modifier = Modifier.size(itemWidth24),
                                onClick = onIncrement
                            ) {
                                Icon(
                                    CoffeeIcons.Add,
                                    contentDescription = stringResource(R.string.increase_button),
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    } else {
                        FilledIconButton(onClick = onIncrement, modifier = Modifier.size(itemWidth24)) {
                            Icon(
                                CoffeeIcons.Add,
                                contentDescription = stringResource(R.string.increase_button),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun CoffeeCardPreview() {
    CoffeeTheme {
        CoffeeCard(
            imageUrl = "",
            title = "Espresso",
            price = 200,
            amount = 0,
            onIncrement = {}
        ) { }
    }
}