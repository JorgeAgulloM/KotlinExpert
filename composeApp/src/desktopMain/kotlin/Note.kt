import Note.Type.*

data class Note(
    val title: String,
    val description: String = "default description",
    val type: Type = TEXT
) {
    enum class Type { TEXT, AUDIO }
}

fun getNotes(): List<Note> = (1..10).map {
    Note(
        title = "Title $it",
        description = "Description $it",
        type = if (it % 3 == 0) AUDIO else TEXT
    )
}

fun getNotes(callBack: (List<Note>) -> Unit) {
    Thread.sleep(2000)
    val notes = (1..10).map {
        Note(
            title = "Title $it",
            description = "Description $it",
            type = if (it % 3 == 0) AUDIO else TEXT
        )
    }
    callBack(notes)
}

fun test() {
    Note("Title")
    Note("Title", "Description")
    Note("Title", "Description", AUDIO)
    Note(title = "Title", type = AUDIO)
}