package ui.screen.home

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.runtime.*

@Composable
fun TopBar() {
    TopAppBar(
        title = { Text(text = "My notes") },
        actions = {
            FilterAction()
        }
    )
}

@Composable
private fun FilterAction() {
    var expanded by remember { mutableStateOf(false) }
    IconButton(onClick = { expanded = true }) {
        Icon(
            imageVector = Icons.Default.FilterList,
            contentDescription = "Filter"
        )
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(onClick = { expanded = false }) {
                Text(text = "All")
            }
            DropdownMenuItem(onClick = { expanded = false }) {
                Text(text = "Text")
            }
            DropdownMenuItem(onClick = { expanded = false }) {
                Text(text = "Audio")
            }
        }
    }
}
