package ui.screen.home.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import data.Note

@Composable
fun Detail(id: Long, onClose: () -> Unit) {

    var note by remember {
        mutableStateOf(
            Note(
                title = "",
                description = "",
                type = Note.Type.TEXT,
                id = id
            )
        )
    }

    Scaffold(
        topBar = {
            TopBar(
                note = note,
                onClose = onClose,
                onSave = onClose,
                onDelete = onClose
            )
        }
    ) {
        Column(modifier = Modifier.padding(32.dp)) {
            OutlinedTextField(
                value = note.title,
                onValueChange = { note = note.copy(title = it) },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Title") },
                maxLines = 1
            )
            TypeDropDown(value = note.type, modifier = Modifier.fillMaxWidth()) { note = note.copy(type = it) }
            OutlinedTextField(
                value = note.description,
                onValueChange = { note = note.copy(description = it) },
                modifier = Modifier.fillMaxWidth().weight(1f),
                label = { Text(text = "Title") },
            )
        }
    }
}

@Composable
private fun TypeDropDown(value: Note.Type, modifier: Modifier = Modifier, onValueChange: (Note.Type) -> Unit) {

    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier, propagateMinConstraints = true) {
        OutlinedTextField(
            value = value.toString(),
            onValueChange = {},
            readOnly = true,
            label = { Text("Type") },
            trailingIcon = {
                IconButton(onClick = { expanded = true }) {
                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Show note types")
                }
            }
        )
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            Note.Type.entries.forEach {
                DropdownMenuItem(onClick = { onValueChange(it); expanded = false }) {
                    Text(text = it.name)
                }
            }
        }
    }
}

@Composable
private fun TopBar(note: Note, onClose: () -> Unit, onSave: () -> Unit, onDelete: () -> Unit) {
    TopAppBar(
        title = { Text(text = note.title) },
        navigationIcon = {
            IconButton(onClick = onClose) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = "Close Detail"
                )
            }
        },
        actions = {
            IconButton(onClick = onSave) {
                Icon(imageVector = Icons.Default.Save, contentDescription = "Save Notes")
            }
            if (note.id != Note.NEW_NOTE) IconButton(onClick = onDelete) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Notes")
            }

        }
    )
}