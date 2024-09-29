package data

import data.Note.Type.TEXT
import kotlinx.serialization.Serializable

@Serializable
data class Note(
    val id: Long = -1L,
    val title: String,
    val description: String = "default description",
    val type: Type = TEXT
) {
    companion object {
        const val NEW_NOTE = -1L
    }
    enum class Type { TEXT, AUDIO }
}
