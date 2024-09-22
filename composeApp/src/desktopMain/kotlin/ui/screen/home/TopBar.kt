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

    /*@Composable
    infix fun Filter.ToMenuItem(label: String) { // No recomendable como uso habitual sobre to_do para composable
        DropdownMenuItem(onClick = {
            expanded = false
            onFilterClick(this)
        }) {
            Text(text = label)
        }
    }*/

    IconButton(onClick = { expanded = true }) {
        Icon(
            imageVector = Icons.Default.FilterList,
            contentDescription = "Filter"
        )
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
/*          Filter.All ToMenuItem "All"  // No recomendable como uso habitual
            Filter.ByType(TEXT) ToMenuItem "Text"
            Filter.ByType(AUDIO) ToMenuItem "Audio"
            */

            listOf(
                Filter.All to "All",
                Filter.ByType(TEXT) to "Text",
                Filter.ByType(AUDIO) to "Audio"
            ).forEach { (filter, text) ->
                DropdownMenuItem(onClick = {
                    expanded = false
                    onFilterClick(filter)
                }) {
                    Text(text = text)
                }
            }
        }
    }
}
