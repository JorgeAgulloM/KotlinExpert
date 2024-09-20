import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

fun main(): Unit = runBlocking {
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