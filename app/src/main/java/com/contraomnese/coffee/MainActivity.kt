package com.contraomnese.coffee

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.contraomnese.coffee.core.ui.widgets.LoadingIndicator
import com.contraomnese.coffee.design.theme.CoffeeTheme
import com.contraomnese.coffee.design.theme.itemWidth64
import com.contraomnese.coffee.navigation.CoffeeHost
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KoinAndroidContext {
                CoffeeApp()
            }
        }
    }
}

@Composable
internal fun CoffeeApp(
    viewModel: MainActivityViewModel = koinViewModel()
) {

    val context = LocalContext.current
    val snackBarHostState = remember { SnackbarHostState() }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()


    LaunchedEffect(Unit) {
        viewModel.notificationEvents.collect { messageResId ->
            val message = context.getString(messageResId)
            snackBarHostState.showSnackbar(
                message = message,
                duration = SnackbarDuration.Short
            )
        }
    }

    CoffeeTheme {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
        ) {
            when  {
                uiState.isLoading -> LoadingIndicator()
                else -> CoffeeHost(snackBarHostState = snackBarHostState, uiState = uiState, setTopBar = { viewModel.onEvent(MainActivityEvent.SetTopBar(it)) })
            }
        }
    }
}