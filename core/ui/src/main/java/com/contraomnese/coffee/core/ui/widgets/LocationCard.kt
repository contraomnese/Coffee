package com.contraomnese.coffee.core.ui.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.contraomnese.coffee.design.R
import com.contraomnese.coffee.design.theme.CoffeeTheme
import com.contraomnese.coffee.design.theme.cornerSize5
import com.contraomnese.coffee.design.theme.elevation4
import com.contraomnese.coffee.design.theme.itemHeight71
import com.contraomnese.coffee.design.theme.padding10
import com.contraomnese.coffee.design.theme.padding14
import com.contraomnese.coffee.design.theme.space6


@Composable
fun LocationCard(
    title: String,
    distance: Double? = null,
    modifier: Modifier= Modifier,
) {
    Card(
        shape = RoundedCornerShape(cornerSize5),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = elevation4),
        modifier = modifier
            .fillMaxWidth()
            .height(itemHeight71)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = padding14, start = padding10, end = padding10)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(space6))
                distance?.let {
                    Text(
                        text = stringResource(R.string.distance, it),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

            }
        }
    }
}

@Preview
@Composable
private fun LocationCardPreview() {
    CoffeeTheme {
        LocationCard(
            title = "Коффейня",
            distance = 2.0
        )
    }
}