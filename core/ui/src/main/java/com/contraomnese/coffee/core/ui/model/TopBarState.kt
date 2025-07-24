package com.contraomnese.coffee.core.ui.model

data class TopBarState(
    val title: String,
    val showBackButton: Boolean = false,
    val onBackClicked: (() -> Unit)? = null,
    val showActionButton: Boolean = false,
    val onActionClicked: (() -> Unit)? = null,
)