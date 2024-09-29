package data.remote

import data.Note
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

private const val NOTES_URL = "http://localhost:8080/notes"

object NotesRepository {

    suspend fun saveNote(note: Note) {
        myNotesClient.post(NOTES_URL) {
            setBody(note)
            contentType(ContentType.Application.Json)
        }
    }

    suspend fun getAll(): List<Note> {
        val response = myNotesClient.request(NOTES_URL)
        return response.body()
    }

    suspend fun getById(id: Long): Note {
        val response = myNotesClient.request("$NOTES_URL/$id")
        return response.body()
    }

    suspend fun updateNote(note: Note) {
        myNotesClient.put(NOTES_URL) {
            setBody(note)
            contentType(ContentType.Application.Json)
        }
    }

    suspend fun deleteNote(note: Note) {
        println("@@@@@@@@@ Save note -> (${note.id})")
        myNotesClient.delete("$NOTES_URL/${note.id}")
    }
}
