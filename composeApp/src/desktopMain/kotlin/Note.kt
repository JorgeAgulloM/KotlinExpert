import Note.Type.*

data class Note(val title: String, val description: String, val type: Type = TEXT) {
    enum class Type { TEXT, AUDIO }
}

fun getNotes(): List<Note> = (1..10).map {
    Note(
        title = "title $it",
        description = "Description $it",
        type = if (it % 3 == 0) AUDIO else TEXT
    )
}
