package extras


import androidx.compose.runtime.MutableState

fun MutableState<AppStateExample.UiState>.update(produceValue: () -> AppStateExample.UiState) {
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