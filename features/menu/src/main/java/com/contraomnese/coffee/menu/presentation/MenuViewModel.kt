package com.contraomnese.coffee.menu.presentation

import androidx.compose.runtime.Immutable
import androidx.lifecycle.viewModelScope
import com.contraomnese.coffee.domain.order.usecases.AddToOrderUseCase
import com.contraomnese.coffee.domain.order.usecases.ObserveOrderUseCase
import com.contraomnese.coffee.domain.order.usecases.RemoveFromOrderUseCase
import com.contraomnese.coffee.domain.menu.model.MenuItem
import com.contraomnese.coffee.domain.menu.usecases.GetMenuUseCase
import com.contraomnese.coffee.domain.order.usecases.ClearOrderUseCase
import com.contraomnese.coffee.presentation.architecture.BaseViewModel
import com.contraomnese.coffee.presentation.architecture.UiState
import com.contraomnese.coffee.presentation.notification.NotificationMonitor
import com.contraomnese.coffee.presentation.usecase.UseCaseExecutorProvider
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Immutable
internal data class MenuUiState(
    override val isLoading: Boolean = false,
    val products: ImmutableList<Pair<MenuItem, Int>> = persistentListOf(),
) : UiState {
    override fun loading(): UiState = copy(isLoading = true)
}

@Immutable
internal sealed interface MenuEvent {
    data class AddToOrder(val product: MenuItem) : MenuEvent
    data class RemoveFromOrder(val productId: Int) : MenuEvent
    data object ClearOrder : MenuEvent
}

internal class MenuViewModel(
    private val useCaseExecutorProvider: UseCaseExecutorProvider,
    private val notificationMonitor: NotificationMonitor,
    private val addToOrderUseCase: AddToOrderUseCase,
    private val removeFromOrderUseCase: RemoveFromOrderUseCase,
    private val observeOrderUseCase: ObserveOrderUseCase,
    private val getMenuUseCase: GetMenuUseCase,
    private val clearOrderUseCase: ClearOrderUseCase,
    private val locationId: Int
) : BaseViewModel<MenuUiState, MenuEvent>(useCaseExecutorProvider, notificationMonitor) {

    init {
        execute(getMenuUseCase, locationId, ::updateProducts, ::provideException)
        observe(useCase = observeOrderUseCase, onEach = ::updateProductsByOrder)
    }

    override fun initialState(): MenuUiState = MenuUiState(isLoading = true,)

    override fun onEvent(event: MenuEvent) {
        when (event) {
            is MenuEvent.AddToOrder -> onAddToOrder(event.product)
            is MenuEvent.RemoveFromOrder -> onRemoveFromOrder(event.productId)
            is MenuEvent.ClearOrder -> clearOrder()
        }
    }

    private fun onAddToOrder(product: MenuItem) = execute(addToOrderUseCase, product, onException = ::provideException)

    private fun onRemoveFromOrder(productId: Int) = execute(removeFromOrderUseCase, productId, onException = ::provideException)

    private fun clearOrder() {
        execute(clearOrderUseCase, onException = ::provideException)
    }

    private fun updateProductsByOrder(items: Map<MenuItem, Int>) {
        val updated = uiState.value.products.map { (menuItem, oldCount) ->
            val newCount = items[menuItem] ?: 0
            menuItem to newCount
        }.toPersistentList()

        updateViewState {
            copy(products = updated)
        }
    }

    private fun updateProducts(products: List<MenuItem>) {
        updateViewState { copy(isLoading = false, products = products.map { it to 0 }.toPersistentList()) }
    }
}