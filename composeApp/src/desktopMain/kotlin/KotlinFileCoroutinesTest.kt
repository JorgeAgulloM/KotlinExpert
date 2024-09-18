import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

data class User(val name: String, val friends: List<User> = emptyList())

class UserService {

    suspend fun doLogin(user: String, pass: String): User = withContext(Dispatchers.IO) {
        // Server request
        User("Jorge")
    }

    suspend fun requestCurrentFriends(user: User): List<User> = withContext(Dispatchers.IO) {
        // Server request
        listOf(User("Johnny"), User("Martha"))
    }

    suspend fun requestSuggestedFriends(users: User): List<User> = withContext(Dispatchers.IO) {
        // Server request
        listOf(User("Sven"), User("Cristin"))
    }

}


@DelicateCoroutinesApi
fun myTest() {
    val userService = UserService()

    // No se usa apenas
    runBlocking { //Bloquea el hilo hasta que termine las corrutinas que contenga

    }

    // GlobalScope se ejecuta durante el tiempo de vida completo de la applicación
    // Es recomendado usarlo solo para lanzar corrutinas de Top-Level que necesiten trabajar de tal modo.
    val job = GlobalScope.launch(Dispatchers.Main) {
        println("Starting")
        val user = userService.doLogin("Jorge", "12345")
        val currentFriends: Deferred<List<User>> = async { userService.requestCurrentFriends(user) }
        val suggestedFriends: Deferred<List<User>> = async { userService.requestSuggestedFriends(user) }
        val finalUser = user.copy(friends = currentFriends.await() + suggestedFriends.await())

        // Graciasal async y el uso del .await() conseguimos llamara a cada una de esas lineas sin esperar al resto de los resultados
        // y obtendremos los datos en sus raviables con await() para poder usarlas
        // Es un trabajo en paralelo, lo que nos proporiona un trabajo más rápido

        println(finalUser)
    }

    GlobalScope.launch(Dispatchers.Main) {
        job.join()
        /** El código se ejecutará cuando la corutina anterior termine **/
    }

    job.cancel() //Cancelará la corrutina


    val coroutineScope = object : CoroutineScope {

        val job = Job()

        val job2 = SupervisorJob() //Especial para trabajar con el hilo principal y una UI

        override val coroutineContext: CoroutineContext
            get() = Dispatchers.Main + job

    }


    // Otra forma de implementar lo anterior con un SupervisorScope es usando
    val coroutineScope2 = MainScope()
    coroutineScope2.coroutineContext.job.cancel()

    coroutineScope.launch {
        println("Starting")
        val user = userService.doLogin("Jorge", "12345")
        val currentFriends = userService.requestCurrentFriends(user)
        val suggestedFriends = userService.requestSuggestedFriends(user)
        val finalUser = user.copy(friends = currentFriends + suggestedFriends)
        println(finalUser)
    }

    // Esto debe hacerse cuando el tiempo de vida del componente al que pertenece finalize
    coroutineScope.job.cancel()

}