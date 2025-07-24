package com.contraomnese.coffee.order.presentation

import androidx.compose.runtime.Immutable
import androidx.lifecycle.viewModelScope
import com.contraomnese.coffee.domain.menu.model.MenuItem
import com.contraomnese.coffee.domain.order.usecases.AddToOrderUseCase
import com.contraomnese.coffee.domain.order.usecases.ClearOrderUseCase
import com.contraomnese.coffee.domain.order.usecases.ObserveOrderUseCase
import com.contraomnese.coffee.domain.order.usecases.RemoveFromOrderUseCase
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
internal data class OrderUiState(
    override val isLoading: Boolean = false,
    val products: ImmutableList<Pair<MenuItem, Int>> = persistentListOf(),
    val isOrderPrepared: Boolean = false,
) : UiState {
    override fun loading(): UiState = copy(isLoading = true)
}

@Immutable
internal sealed interface OrderEvent {
    data class AddToOrder(val product: MenuItem) : OrderEvent
    data class RemoveFromOrder(val productId: Int) : OrderEvent
    data object ClearOrder : OrderEvent
}

internal class OrderViewModel(
    private val useCaseExecutorProvider: UseCaseExecutorProvider,
    private val notificationMonitor: NotificationMonitor,
    private val addToOrderUseCase: AddToOrderUseCase,
    private val removeFromOrderUseCase: RemoveFromOrderUseCase,
    private val observeOrderUseCase: ObserveOrderUseCase,
    private val clearOrderUseCase: ClearOrderUseCase,
) : BaseViewModel<OrderUiState, OrderEvent>(useCaseExecutorProvider, notificationMonitor) {

    init {
        observe(useCase = observeOrderUseCase, onEach = ::updateProductsByOrder)
    }

    override fun initialState(): OrderUiState = OrderUiState(isLoading = true)

    override fun onEvent(event: OrderEvent) {
        when (event) {
            is OrderEvent.AddToOrder -> onAddToOrder(event.product)
            is OrderEvent.RemoveFromOrder -> onRemoveFromOrder(event.productId)
            is OrderEvent.ClearOrder -> clearOrder()
        }
    }

    private fun onAddToOrder(product: MenuItem) = execute(addToOrderUseCase, product, onException = ::provideException)

    private fun onRemoveFromOrder(productId: Int) =
        execute(removeFromOrderUseCase, productId, onException = ::provideException)

    private fun clearOrder() {
        execute(clearOrderUseCase, onSuccess = ::updateOrderPreparedStatus, onException = ::provideException)
        viewModelScope.launch {
            delay(2000)
            updateViewState { copy(isLoading = false) }
        }
    }

    private fun updateOrderPreparedStatus(value: Unit) {
        updateViewState { copy(isLoading = true, isOrderPrepared = true) }
    }

    private fun updateProductsByOrder(items: Map<MenuItem, Int>) {
        updateViewState {
            copy(products = items.map { it.key to it.value }.toPersistentList(), isLoading = false)
        }
    }
}