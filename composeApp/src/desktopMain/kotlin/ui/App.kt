package ui

import androidx.compose.runtime.*
import ui.screen.home.Home
import ui.screen.home.HomeViewModel
import ui.screen.home.detail.Detail
import ui.screen.home.detail.DetailViewModel

sealed interface Route {
    data object Home : Route
    data class Detail(val id: Long) : Route
}

@Composable
fun App() {

    var route by remember { mutableStateOf<Route>(Route.Home) }
    val scope = rememberCoroutineScope()

    route.let {
        when (it) {
            Route.Home -> Home(
                viewModel = HomeViewModel(scope),
                onNoteClick = { noteId -> route = Route.Detail(noteId) }
            )
            is Route.Detail -> Detail(
                viewModel = DetailViewModel(scope = scope, noteId = it.id),
                onClose = { route = Route.Home }
            )
        }
    }

}
