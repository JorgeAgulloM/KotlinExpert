import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlin.concurrent.thread

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
    //val notes = mutableStateOf(getNotes())
    val state = mutableStateOf(UiState())
    fun loadNotes() {
        //Solo es un truco para la prueba
        thread {
            state.value = UiState(loading = true)
            getNotes { state.value = UiState(notes = it, loading = false) }
        }
    }

    data class UiState(
        val notes: List<Note>? = null,
        val loading: Boolean = false
    )
/*    val text = mutableStateOf("")
    val buttonEnabled: Boolean
        get() = text.value.isNotEmpty()


    fun isButtonEnabled() = text.value.isNotEmpty()*/
}