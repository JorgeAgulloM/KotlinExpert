import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
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

fun main_old3(): Unit = runBlocking {
    val viewModelShareFlow = ViewModelShareFlow()

    launch { viewModelShareFlow.update() }
    delay(2100) // Al no escuchar desde el inicio, no obtendremos los primeros valores
    viewModelShareFlow.state.collect {
        delay(1000) //Solo imprimiremos cada segundo, por lo que llegaremos tarde y nos vamos a saltar un valor
        println(it)
    }
}

class ChannelsAsFlows {

    /**
     * Los channel son métodos ya sustituidos por los flow para realizar la reactividad. La grandiferencia es que cuando
     * los channel envian nueva información, solo obtendrá dicha info el collector más rápido, al contrario que los
     * stateFlow en los que todos los colectores reciven el nuevo valor una vez emitido. Aún así podriamos usar channels,
     * realizando un comportamiento parecido a los flow, en algunas ocasiones, con la diferencia de que no se emitirán
     * nuevos valores hasta que se consuma el valor actual, por defecto, aunque esto puede configurarse.
     *
     * Configuramos la propertie como channel y el valor público como reciveAsFlow() y con esto obtendremos un
     * comportamiento como los flow de puertas hacia afuera.
     *
     * Podemos configurar:
     *   . BUFFERED (-1): que nos permite almacenar hasta 64 valores aunque no se estén colecteando, una vez que se inicie
     *       el collect, y el valor más antiguo se descartge, se continuará con la emisión.
     *   . Capacidad deseada: Podemos config un valor de 3 por ejemplo, y solo almacenará 3 valores más el emitido.
     *   . onBufferOverflow: Similar a la propiedad del flow
     **/

    private val _state: Channel<Note> = Channel()
    val state: Flow<Note> = _state.receiveAsFlow()
    val stateChannel: Channel<Note> = _state

    suspend fun update() {
        var count = 1
        while (!_state.isClosedForSend) {
            delay(500)
            _state.send(Note("Title $count", "description $count", Note.Type.TEXT))
            println("Emitting Title $count")
            count++
            if (count > 6)
                _state.close()
        }
    }
}


fun main_old4(): Unit = runBlocking {
    val channelsAsFlows = ChannelsAsFlows()

    launch { channelsAsFlows.update() }
    delay(2100) // Al no escuchar desde el inicio, no obtendremos los primeros valores
    /*
        channelsAsFlows.state.collect {
            delay(1000) //Solo imprimiremos cada segundo, por lo que llegaremos tarde y nos vamos a saltar un valor
            println(it)
        }
    */

    launch {
        for (value in channelsAsFlows.stateChannel) {
            delay(1000)
            println(value)
        }
    }
}

/**
 * Aquí se plantea comom integrar un servicio que emite a través de un callBack y obtener de forma similar y
 * transparente sus valores como si fuese un flow, sobre to_do en los casos en los que tengamos además otros flows y
 * queramos que no haya distinción a la ora de conectarnos a dicho servicio y su callBack
 **/


class ViewModelCallBack {
    fun update(callback: (Note) -> Unit) {
        var count = 1
        while (true) {
            Thread.sleep(500)
            callback(Note("Title $count", "description $count", Note.Type.TEXT))
            println("Emitting Title $count")
            count++
        }
    }
}

fun ViewModelCallBack.updateFlow(): Flow<Note> = callbackFlow {
    update { trySend(it) }
}

fun main(): Unit = runBlocking {
    val viewModel = ViewModelCallBack()
    launch(Dispatchers.Default) { viewModel.updateFlow().collect(::println) }
}







