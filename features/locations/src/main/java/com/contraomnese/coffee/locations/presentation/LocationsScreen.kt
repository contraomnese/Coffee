package com.contraomnese.coffee.locations.presentation

import androidx.compose.foundation.clickable
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
import com.contraomnese.coffee.core.ui.utils.DistanceCalculator
import com.contraomnese.coffee.core.ui.utils.RequestLocationPermission
import com.contraomnese.coffee.core.ui.widgets.LoadingIndicator
import com.contraomnese.coffee.core.ui.widgets.LocationCard
import com.contraomnese.coffee.design.R
import com.contraomnese.coffee.design.theme.CoffeeTheme
import com.contraomnese.coffee.design.theme.cornerSize24
import com.contraomnese.coffee.design.theme.defaultButtonHeight
import com.contraomnese.coffee.design.theme.padding16
import com.contraomnese.coffee.design.theme.space24
import com.contraomnese.coffee.design.theme.space6
import com.contraomnese.coffee.design.theme.space8
import com.contraomnese.coffee.domain.locations.model.LocationItem
import kotlinx.collections.immutable.toPersistentList

@Composable
internal fun LocationsRoute(
    viewModel: LocationsViewModel,
    setTopBar: (TopBarState) -> Unit,
    onNavigateToMap: () -> Unit,
    onNavigateToMenu: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        setTopBar(
            TopBarState(
                title = context.getString(R.string.locations_title),
                showActionButton = true,
                onActionClicked = onNavigateToMap
            )
        )
    }

    RequestLocationPermission {
        viewModel.onEvent(LocationsEvent.RequestUserLocation)
    }

    when {
        uiState.isLoading -> LoadingIndicator()
        else -> LocationsScreen(
            uiState = uiState,
            onEvent = viewModel::onEvent,
            onMapClicked = onNavigateToMap,
            onLocationClicked = onNavigateToMenu,
            modifier = modifier
        )
    }
}

@Composable
internal fun LocationsScreen(
    uiState: LocationsUiState,
    onEvent: (LocationsEvent) -> Unit = {},
    onMapClicked: () -> Unit = {},
    onLocationClicked: (Int) -> Unit = {},
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
            items(uiState.locations, key = { it.id }) { location ->
                LocationCard(
                    modifier = Modifier.clickable { onLocationClicked(location.id) },
                    title = location.title,
                    distance = uiState.userLocation?.let {
                        DistanceCalculator.distanceInKilometers(
                            userLat = it.latitude,
                            userLon = it.longitude,
                            targetLat = location.latitude,
                            targetLon = location.longitude
                        )
                    }  ?: 0.0
                )
            }
            item {
                Spacer(Modifier.height(space8))
            }
        }
        FloatingActionButton(
            modifier = modifier
                .fillMaxWidth()
                .requiredHeight(defaultButtonHeight),
            onClick = onMapClicked,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            shape = RoundedCornerShape(cornerSize24)
        ) {
            Text(
                text = stringResource(R.string.on_map),
                style = MaterialTheme.typography.labelLarge,
            )
        }
    }
}

@Preview
@Composable
private fun MenuScreenPreview() {
    CoffeeTheme {
        LocationsScreen(
            uiState = LocationsUiState(
                locations = listOf(
                    LocationItem(
                        id = 1,
                        title = "Coffee1",
                        latitude = 4.5,
                        longitude = 6.7
                    ),
                    LocationItem(
                        id = 2,
                        title = "Coffee2",
                        latitude = 4.5,
                        longitude = 6.7
                    )
                ).toPersistentList()
            )
        )
    }
}