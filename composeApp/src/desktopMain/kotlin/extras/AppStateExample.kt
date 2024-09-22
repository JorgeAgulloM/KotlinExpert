package extras

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

class AppStateExample {
    //val notes = mutableStateOf(getNotes())
    //val state = mutableStateOf(UiState())
    //var state: UiState by mutableStateOf(UiState())
    private val _state = MutableStateFlow(UiState())
    val state = _state.asStateFlow()


    fun loadNotes(coroutineScope: CoroutineScope) {
        //Solo es un truco para la prueba
/*        thread {
            //state.value = UiState(loading = true)
            state = UiState(loading = true)
            //Opción 2 state.updateGeneric { UiState(loading = true) }
            //Opción 3 state.updateGenericShort { it.copy(loading = true) }
            //getNotes { state.value = UiState(notes = it, loading = false) }
            getNotes { notes -> state = UiState(notes = notes) }
        }*/

        coroutineScope.launch {
            _state.value = UiState(loading = true)

            getNotesAsFlow().collect { notes ->
                _state.value = UiState(notes = notes)
            }
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