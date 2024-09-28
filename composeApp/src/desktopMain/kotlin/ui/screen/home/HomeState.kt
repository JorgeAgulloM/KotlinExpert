package ui.screen.home

import data.Filter
import data.Note
import data.remote.myNotesClient

import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

object HomeState {
    private val _state = MutableStateFlow(UiState())
    val state = _state.asStateFlow()

    fun getServerNotes(coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            _state.value = UiState(loading = true)
            val response = myNotesClient.request("http://localhost:8080/notes")
            _state.value = UiState(notes = response.body())
        }
    }

    fun onFilteredClick(filter: Filter) {
        _state.update { it.copy(filter = filter) }
    }

    data class UiState(
        val notes: List<Note>? = null,
        val loading: Boolean = false,
        val filter: Filter = Filter.All
    ) {
        val filterNotes: List<Note>?
            get() = when (filter) {
                Filter.All -> notes
                is Filter.ByType -> notes?.filter { it.type == filter.type }
            }
    }
}
