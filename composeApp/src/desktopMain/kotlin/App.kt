import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import mynotes.composeapp.generated.resources.Res
import mynotes.composeapp.generated.resources.compose_multiplatform
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalResourceApi::class)
@Composable
@Preview
fun App() {
    val textButton = remember { mutableStateOf("Click me!") }
    val text = remember { mutableStateOf("") }
    val message = buildMessage(text.value)
    val buttonEnabled = text.value.isNotEmpty()

    MaterialTheme {
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
            TextField(value = text.value, onValueChange = { newText -> text.value = newText })
            Text(text = message)

            Button(
                onClick = {
                    textButton.value = if (textButton.value != "Clear") "Clear" else "Click me!"
                    text.value = ""
                },
                enabled = buttonEnabled
            ) {
                Text(text = textButton.value)
            }

        }
    }
}

fun buildMessage(message: String): String = "Hello $message"
