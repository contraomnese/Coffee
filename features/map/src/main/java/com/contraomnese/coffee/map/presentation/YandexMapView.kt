package com.contraomnese.coffee.map.presentation

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.MapObject
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.mapview.MapView

private const val INIT_MAX_ZOOM = 20f
private const val INIT_MIN_ZOOM = 7f

private const val DEFAULT_ANIMATION_DURATION = 0.3f

@Composable
internal fun YandexMapView(
    uiState: MapUiState,
    onNavigateToMenu: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val mapViewRef = remember { mutableStateOf<MapView?>(null) }
    val backgroundColor = MaterialTheme.colorScheme.primaryContainer.toArgb()

    AndroidView(
        modifier = modifier,
        factory = {
            MapKitFactory.getInstance().onStart()
            val mapView = MapView(it)
            initializeMapSettings(mapView)

            val initialPoint = Point(55.751244, 37.618423)
            mapView.mapWindow.map.move(
                CameraPosition(initialPoint, uiState.zoomValue, uiState.azimuth, uiState.tilt)
            )

            mapViewRef.value = mapView
            mapView
        },
        onRelease = {
            it.onStop()
        }
    )

    LaunchedEffect(uiState.zoomValue) {
        mapViewRef.value?.mapWindow?.map?.let { map ->
            val currentCameraPosition = map.cameraPosition
            val target = currentCameraPosition.target
            map.move(
                CameraPosition(target, uiState.zoomValue, currentCameraPosition.azimuth, currentCameraPosition.tilt),
                Animation(
                    Animation.Type.SMOOTH,
                    DEFAULT_ANIMATION_DURATION
                ), null
            )
        }
    }

    LaunchedEffect(uiState.locations) {
        val map = mapViewRef.value?.mapWindow?.map
        map?.let {
            uiState.locations.forEach { location ->
                val point = Point(location.latitude, location.longitude)
                val placeMark = map.mapObjects.addPlacemark().apply {
                    geometry = point
                    setIcon(MapMarkerImageProvider(context, label = location.title, backgroundColor))
                    opacity = 1f
                    isDraggable = false
                }
                placeMark.addTapListener(object : MapObjectTapListener {
                    override fun onMapObjectTap(p0: MapObject, p1: Point): Boolean {
                        onNavigateToMenu(location.id)
                        return true
                    }
                })
            }
        }
    }


}

private fun initializeMapSettings(mapView: MapView) {
    mapView.mapWindow.let { window ->
        window.map.cameraBounds.setMaxZoomPreference(INIT_MAX_ZOOM)
        window.map.cameraBounds.setMinZoomPreference(INIT_MIN_ZOOM)
    }
}