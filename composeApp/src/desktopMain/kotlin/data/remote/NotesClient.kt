package data.remote

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*


val myNotesClient = HttpClient(OkHttp) { // Aquí añadimos el motor http que deseamos en cada plataforma
    install(ContentNegotiation) {
        json()
    }
}