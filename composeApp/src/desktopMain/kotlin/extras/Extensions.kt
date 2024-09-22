package extras

import AppState
import androidx.compose.runtime.MutableState

fun MutableState<AppState.UiState>.update(produceValue: () -> AppState.UiState) {
    this.value = produceValue()
}

//Generic extension function
fun <T> MutableState<T>.updateGeneric(produceValue: () -> T) {
    this.value = produceValue()
}

//En este caso  no es necesario usar el "this"

//otra opción para llamar a la lambda en el objeto donde se utilice esta función
fun <T> MutableState<T>.updateGenericShort(produceValue: (T) -> T) {
    value = produceValue(value)
}