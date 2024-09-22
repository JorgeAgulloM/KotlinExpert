package extras

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

data class Note(
    val title: String,
    val description: String = "default description",
    val type: Type = Type.TEXT
) {
    enum class Type { TEXT, AUDIO }
}

fun getNotes(): List<Note> = (1..10).map {
    Note(
        title = "Title $it",
        description = "Description $it",
        type = if (it % 3 == 0) Note.Type.AUDIO else Note.Type.TEXT
    )
}

fun getNotes(callBack: (List<Note>) -> Unit) {
    Thread.sleep(2000)
    val notes = (0..10).map {
        Note(
            title = "Title $it",
            description = "Description $it",
            type = if (it % 3 == 0) Note.Type.AUDIO else Note.Type.TEXT
        )
    }
    callBack(notes)
}

suspend fun getNotesWithCoroutine() = withContext(Dispatchers.IO) {
    delay(2000)
    (0..10).map {
        Note(
            title = "Title $it",
            description = "Description $it",
            type = if (it % 3 == 0) Note.Type.AUDIO else Note.Type.TEXT
        )
    }
}

fun test() {
    Note("Title")
    Note("Title", "Description")
    Note("Title", "Description", Note.Type.AUDIO)
    Note(title = "Title", type = Note.Type.AUDIO)
}

fun getNotesAsFlow(): Flow<List<Note>> = flow {
    delay(2000)
    var notes = emptyList<Note>()
    (0..10).forEach {
        notes = notes + Note(
            "Title $it",
            "Description $it",
            if (it % 3 == 0) Note.Type.AUDIO else Note.Type.TEXT
        )
        emit(notes)
        delay(500)
    }
}