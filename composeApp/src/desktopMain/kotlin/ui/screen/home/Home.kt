package ui.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun Home(onCreatedClick: () -> Unit): Unit = with(HomeState) {

    val state by state.collectAsState()

    LaunchedEffect(true) {
        getServerNotes(this)
    }

    MaterialTheme {
        Scaffold(
            topBar = { TopBar(::onFilteredClick) },
            floatingActionButton = {
                FloatingActionButton(onClick = onCreatedClick) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add Note")
                }
            }
        ) { paddings ->
            Box(
                modifier = Modifier.fillMaxSize().padding(paddings),
                contentAlignment = Alignment.Center
            ) {
                if (state.loading)
                    CircularProgressIndicator()

                state.filterNotes?.let { NotesList(it) }
            }
        }
    }
}
