import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() {
    val appState = AppState()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "MyNotes",
        ) {
            App(appState)
        }
    }
}

class AppState {
    val text = mutableStateOf("")
    val buttonEnabled: Boolean
        get() = text.value.isNotEmpty()


    fun isButtonEnabled() = text.value.isNotEmpty()
}