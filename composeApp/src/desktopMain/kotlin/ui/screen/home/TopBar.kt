package ui.screen.home

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.runtime.*
import data.Filter
import data.Note.Type.AUDIO
import data.Note.Type.TEXT

@Composable
fun TopBar(onFilterClick: (Filter) -> Unit) {
    TopAppBar(
        title = { Text(text = "My notes") },
        actions = { FilterAction(onFilterClick) }
    )
}

@Composable
private fun FilterAction(onFilterClick: (Filter) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    IconButton(onClick = { expanded = true }) {
        Icon(
            imageVector = Icons.Default.FilterList,
            contentDescription = "Filter"
        )
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(onClick = {
                expanded = false
                onFilterClick(Filter.All)
            }) {
                Text(text = "All")
            }
            DropdownMenuItem(onClick = {
                expanded = false
                onFilterClick(Filter.ByType(TEXT))
            }) {
                Text(text = "Text")
            }
            DropdownMenuItem(onClick = {
                expanded = false
                onFilterClick(Filter.ByType(AUDIO))
            }) {
                Text(text = "Audio")
            }
        }
    }
}
