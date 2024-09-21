import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

fun main_old(): Unit = runBlocking {
    val res = flow {
        emit(1)
        delay(500)
        emit(2)
        delay(500)
        emit(3)
        delay(500)
        emit(4)
        delay(500)
        emit(5)
    }
        //.filter { it % 2 == 0 }
        //.map { "Item $it" }
        .transform { if (it % 2 == 0) emit("Item $it") } //Mejora el uso de filter y map juntos
    launch {
        delay(500)
        res.collect {
            println(it)
        }
    }
    delay(500)
    res.collect {
        println(it)
    }
    delay(500)
    res.collect {
        println(it)
    }

    var flow1 = flowOf(1, 2, 3, 4)
    var flow2 = flowOf("a", "b", "c")

    flow1.zip(flow2) { a, b -> "$a -> $b" }.collect { println(it) }
    flow1.combine(flow2) { a, b -> "$a -> $b" }.collect { println(it) }

    flow1 = flowOf(1, 2, 3, 4).onEach { delay(200) }
    flow2 = flowOf("a", "b", "c").onEach { delay(400) }
    flow1.combine(flow2) { a, b -> "$a -> $b" }.collect { println(it) }
    println(flow1.combine(flow2) { a, b -> "$a -> $b" }.toList())


    //No podemos cambiar de contexto
    val myFlow = flow {
        withContext(Dispatchers.IO) {
            emit(21)
        }
    }

    myFlow
        .catch { throwable ->
            println(throwable.message)
        }
        .collect {
            println(it)
        }

    //Podemos definir el contexto en el colector
    val myFlow2 = flow {
        emit(22)
    }

    myFlow2
        .flowOn(Dispatchers.IO)
        .collect {
            println(it)
        }
}


class ViewModel {
    private val _state: MutableStateFlow<Note> = MutableStateFlow(Note("title 1", "Description 1", Note.Type.TEXT))
    val state: StateFlow<Note> = _state
    suspend fun update() {
        var count = 1
        while (true) {
            delay(2000)
            count++
            _state.value = Note("Title $count", "description $count", Note.Type.TEXT)
        }
    }
}

fun main(): Unit = runBlocking {
    val viewModel = ViewModel()

    launch { viewModel.update() }
    viewModel.state.collect(::println)
}








