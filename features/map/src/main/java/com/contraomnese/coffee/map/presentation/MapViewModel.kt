package com.contraomnese.coffee.map.presentation

import androidx.compose.runtime.Immutable
import com.contraomnese.coffee.domain.locations.model.LocationItem
import com.contraomnese.coffee.domain.locations.usecases.GetLocationsUseCase
import com.contraomnese.coffee.presentation.architecture.BaseViewModel
import com.contraomnese.coffee.presentation.architecture.UiState
import com.contraomnese.coffee.presentation.notification.NotificationMonitor
import com.contraomnese.coffee.presentation.usecase.UseCaseExecutorProvider
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

@Immutable
internal data class MapUiState(
    override val isLoading: Boolean = false,
    val locations: ImmutableList<LocationItem> = persistentListOf(),
    val zoomValue: Float = 12f,
    val azimuth: Float = 0f,
    val tilt: Float = 0f,
    val currentLocation: Int? = null
) : UiState {
    override fun loading(): UiState = copy(isLoading = true)
}

@Immutable
internal sealed interface MapEvent {
    data object ZoomIn : MapEvent
    data object ZoomOut : MapEvent
}

internal class MapViewModel(
    private val useCaseExecutorProvider: UseCaseExecutorProvider,
    private val notificationMonitor: NotificationMonitor,
    private val getLocationsUseCase: GetLocationsUseCase
) : BaseViewModel<MapUiState, MapEvent>(useCaseExecutorProvider, notificationMonitor) {

    init {
        execute(getLocationsUseCase, ::updateLocations, ::provideException)
    }

    override fun initialState(): MapUiState = MapUiState()

    override fun onEvent(event: MapEvent) {
        when (event) {
            is MapEvent.ZoomIn -> zoomIn()
            is MapEvent.ZoomOut -> zoomOut()
        }
    }

    private fun zoomIn() {
        updateViewState { copy(zoomValue = zoomValue + 1f) }
    }

    private fun zoomOut() {
        updateViewState { copy(zoomValue = zoomValue - 1f) }
    }

    private fun updateLocations(locations: List<LocationItem>) {
        updateViewState { copy(isLoading = false, locations = locations.toPersistentList()) }
    }
}