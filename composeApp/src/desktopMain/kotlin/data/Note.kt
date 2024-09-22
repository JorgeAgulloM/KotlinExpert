package data

import data.Note.Type.AUDIO
import data.Note.Type.TEXT
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

data class Note(
    val title: String,
    val description: String = "default description",
    val type: Type = TEXT
) {
    enum class Type { TEXT, AUDIO }
}

fun getNotes() = flow {
    delay(2000)
    emit((1..10).map {
        Note(
            title = "Title $it",
            description = "Description $it",
            type = if (it % 3 == 0) AUDIO else TEXT
        )
    })
}