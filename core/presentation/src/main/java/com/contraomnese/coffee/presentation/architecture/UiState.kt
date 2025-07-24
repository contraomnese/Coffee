package com.contraomnese.coffee.presentation.architecture

interface UiState {
    val isLoading: Boolean
    fun loading(): UiState
}