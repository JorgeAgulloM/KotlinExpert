package ui.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun Home(): Unit = with(HomeState) {

    val state by state.collectAsState()

    LaunchedEffect(true) {
        loadNotes(this)
    }

    MaterialTheme {
        Scaffold(topBar = { TopBar(::onFilteredClick) }) { paddings ->
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
