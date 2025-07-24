package com.contraomnese.coffee.presentation.notification

import kotlinx.coroutines.CoroutineScope

typealias NotificationMonitorProvider = @JvmSuppressWildcards (coroutineScope: CoroutineScope) -> NotificationMonitor