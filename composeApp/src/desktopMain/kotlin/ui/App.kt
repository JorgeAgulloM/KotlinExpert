package ui

import androidx.compose.runtime.*
import ui.screen.home.Home
import ui.screen.home.detail.Detail

sealed interface Route {
    data object Home : Route
    data class Detail(val id: Long) : Route
}

@Composable
fun App() {

    var route by remember { mutableStateOf<Route>(Route.Home) }

    route.let {
        when (it) {
            Route.Home -> Home(onCreatedClick = { route = Route.Detail(-1) })
            is Route.Detail -> Detail(it.id, onClose = { route = Route.Home})
        }
    }

}
