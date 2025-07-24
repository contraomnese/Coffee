package com.contraomnese.coffee.map.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.zIndex
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.contraomnese.coffee.core.ui.model.TopBarState
import com.contraomnese.coffee.core.ui.widgets.LoadingIndicator
import com.contraomnese.coffee.design.R
import com.contraomnese.coffee.design.icons.CoffeeIcons
import com.contraomnese.coffee.design.theme.itemWidth48
import com.contraomnese.coffee.design.theme.padding8
import com.contraomnese.coffee.design.theme.space24
import com.yandex.mapkit.MapKitFactory

@Composable
internal fun MapRoute(
    viewModel: MapViewModel,
    setTopBar: (TopBarState) -> Unit,
    onNavigateToBack: () -> Unit,
    onNavigateToMenu: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        setTopBar(
            TopBarState(
                title = context.getString(R.string.map_title),
                showBackButton = true,
                onBackClicked = onNavigateToBack
            )
        )
    }

    when {
        uiState.isLoading -> LoadingIndicator()
        else -> MapScreen(uiState = uiState, onEvent = viewModel::onEvent, onPlaceMarkClicked = onNavigateToMenu)
    }

}

@Composable
internal fun MapScreen(
    uiState: MapUiState,
    onEvent: (MapEvent) -> Unit = {},
    onPlaceMarkClicked: (Int) -> Unit,
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val lifecycle = lifecycleOwner.lifecycle

        val observer = object : DefaultLifecycleObserver {
            override fun onStart(owner: LifecycleOwner) {
                MapKitFactory.getInstance().onStart()
            }

            override fun onStop(owner: LifecycleOwner) {
                MapKitFactory.getInstance().onStop()
            }
        }

        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        YandexMapView(modifier = Modifier.fillMaxSize(), uiState = uiState, onNavigateToMenu = onPlaceMarkClicked)
        Column(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = padding8),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(space24)
        ) {
            FilledIconButton(
                onClick = { onEvent(MapEvent.ZoomIn) }, modifier = Modifier
                    .size(itemWidth48)
                    .zIndex(1f)
            ) {
                Icon(
                    CoffeeIcons.Add,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                )
            }
            FilledIconButton(
                onClick = { onEvent(MapEvent.ZoomOut) },
                modifier = Modifier
                    .size(itemWidth48)
                    .zIndex(1f)
            ) {
                Icon(
                    CoffeeIcons.Remove,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                )
            }
        }

    }

}