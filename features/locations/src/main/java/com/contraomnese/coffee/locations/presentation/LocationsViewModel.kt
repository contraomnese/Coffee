package com.contraomnese.coffee.locations.presentation

import android.location.Location
import androidx.compose.runtime.Immutable
import com.contraomnese.coffee.core.ui.utils.LocationProvider
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
internal data class LocationsUiState(
    override val isLoading: Boolean = false,
    val locations: ImmutableList<LocationItem> = persistentListOf(),
    val userLocation: Location? = null,
) : UiState {
    override fun loading(): UiState = copy(isLoading = true)
}

@Immutable
internal sealed interface LocationsEvent {
    data object RequestUserLocation: LocationsEvent
}

internal class LocationsViewModel(
    private val useCaseExecutorProvider: UseCaseExecutorProvider,
    private val notificationMonitor: NotificationMonitor,
    private val getLocationsUseCase: GetLocationsUseCase,
    private val locationProvider: LocationProvider,
) : BaseViewModel<LocationsUiState, LocationsEvent>(useCaseExecutorProvider, notificationMonitor) {

    init {
        execute(getLocationsUseCase, ::updateLocations, ::provideException)
    }

    override fun initialState(): LocationsUiState = LocationsUiState(isLoading = true)

    override fun onEvent(event: LocationsEvent) {
        when (event) {
            LocationsEvent.RequestUserLocation -> updateLocation()
        }
    }

    private fun updateLocations(locations: List<LocationItem>) {
        updateViewState { copy(isLoading = false, locations = locations.toPersistentList()) }
    }

    private fun updateLocation() {
        val userLocation = locationProvider.getLastKnownLocation()
        updateViewState { copy(userLocation = userLocation) }
    }

}