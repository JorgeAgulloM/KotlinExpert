import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalResourceApi::class)
@Composable
@Preview
fun App(appState: AppState) {

    val notes = appState.state.value.notes

    if (notes == null) LaunchedEffect(true) {
        appState.loadNotes()
    }

    MaterialTheme {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            if (appState.state.value.loading)
                CircularProgressIndicator()
            if (notes != null)
                NotesList(notes)
        }
    }

    /*val textButton = remember { mutableStateOf("Click me!") }*/
/*    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = { showContent = !showContent }) {
                Text("Click me!")
            }
            AnimatedVisibility(showContent) {
                val greeting = remember { Greeting().greet() }
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(painterResource(Res.drawable.compose_multiplatform), null)
                    Text("Compose: $greeting")
                }
            }
            AnimatedVisibility(!showContent) {
                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Pulsa el botón")
                }
            }

            Text("Write your name")
            TextField(value = appState.text.value, onValueChange = { newText -> appState.text.value = newText })
            Text(text = buildMessage(appState.text.value))

            Button(
                onClick = {
                    textButton.value = if (textButton.value != "Clear") "Clear" else "Click me!"
                    appState.text.value = ""
                },
                enabled = appState.buttonEnabled
            ) {
                Text(text = textButton.value)
            }

        }
    }*/
}

@Composable
private fun NotesList(notes: List<Note>) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(notes) { note ->
            Card(
                modifier = Modifier.padding(8.dp)
                    .fillMaxWidth(0.8f)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Row {
                        Text(
                            text = note.title,
                            modifier = Modifier.weight(1f),
                            style = MaterialTheme.typography.h6
                        )
                        if (note.type == Note.Type.AUDIO)
                            Icon(imageVector = Icons.Default.Mic, contentDescription = null)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = note.description)
                }
            }
        }
    }
}

fun buildMessage(message: String): String = "Hello $message"
