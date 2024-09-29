package ui.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import data.Note

@Composable
fun Home(viewModel: HomeViewModel, onNoteClick: (noteId: Long) -> Unit): Unit {

    MaterialTheme {
        Scaffold(
            topBar = { TopBar(viewModel::onFilteredClick) },
            floatingActionButton = {
                FloatingActionButton(onClick = { onNoteClick(Note.NEW_NOTE) }) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add Note")
                }
            }
        ) { paddings ->
            Box(
                modifier = Modifier.fillMaxSize().padding(paddings),
                contentAlignment = Alignment.Center
            ) {
                if (viewModel.state.loading)
                    CircularProgressIndicator()

                viewModel.state.filterNotes?.let { note -> NotesList(notes = note) { onNoteClick(it.id) } }
            }
        }
    }
}
