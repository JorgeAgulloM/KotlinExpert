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

fun main_old2(): Unit = runBlocking {
    val viewModel = ViewModel()

    launch { viewModel.update() }
    viewModel.state.collect(::println)
}


/**
 * ShareFlow tiene la capacidad de configurar tantos valores como queramos, pero si no lo configuramos no los almacena
 * por defecto, por lo que si nos enganchamos a el "tarde", no observaremos los valores iniciales.
 **/
class ViewModelShareFlow {

    /**
     * Propiedades del ShareFlow:
     * - Replay: No permite configurar la cantidad de valoes que queramos que almacene para que al suscribirnos nos
     * devuelva esa cantidad de valores, por ejemplo, si configuramos a 1, funcionará igual que el StateFlow, y nos
     * devolverá el último valor almacenado inmediatamente seguido del resto que esté emitiendo.
     *
     * - ExtraBufferCapacity: Tiene la capacidad de almacenar más valores y además suspender la emisión hasta que se
     * empieze a consumir nuevos valores
     *
     * - OnBufferOverflow:
     *   . SUSPEND: Por defecto se encuentra en SUSPEND, esto suspende las emisiones si superamos la capacida del buffer.
     *   . DROP_OLDEST: Descarta los valores más antiguos almacenados y permite seguir emitiendo.
     *   . DROP_LATEST: Descarta los valores nuevos conservando los antiguos hasta obtener nuevos consumos.
     *   * Podróiamos realizar un comportamiento contrario al stateFlow para que emita continuamente el mismo valor,
     *   configurando replay = 1 y onBufferOverflow.DROP_OLDEST
     **/

    private val _state: MutableSharedFlow<Note> = MutableSharedFlow(
        replay = 3,
        extraBufferCapacity = 3
    )
    val state: SharedFlow<Note> = _state

    suspend fun update() {
        var count = 1
        while (count <= 20) {
            delay(500)
            _state.emit(Note("Title $count", "description $count", Note.Type.TEXT))
            println("Emitting Title $count")
            count++
        }
    }
}

fun main(): Unit = runBlocking {
    val viewModelShareFlow = ViewModelShareFlow()

    launch { viewModelShareFlow.update() }
    delay(2100) // Al no escuchar desde el inicio, no obtendremos los primeros valores
    viewModelShareFlow.state.collect {
        delay(1000) //Solo imprimiremos cada segundo, por lo que llegaremos tarde y nos vamos a saltar un valor
        println(it)
    }
}








