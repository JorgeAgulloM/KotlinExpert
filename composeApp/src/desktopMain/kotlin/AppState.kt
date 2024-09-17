import androidx.compose.runtime.mutableStateOf
import kotlin.concurrent.thread

class AppState {
    //val notes = mutableStateOf(getNotes())
    val state = mutableStateOf(UiState())
    fun loadNotes() {
        //Solo es un truco para la prueba
        thread {
            //state.value = UiState(loading = true)
            state.update { UiState(loading = true) }
            //Opción 2 state.updateGeneric { UiState(loading = true) }
            //Opción 3 state.updateGenericShort { it.copy(loading = true) }
            //getNotes { state.value = UiState(notes = it, loading = false) }
            getNotes { notes -> state.update { UiState(notes = notes) } }
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