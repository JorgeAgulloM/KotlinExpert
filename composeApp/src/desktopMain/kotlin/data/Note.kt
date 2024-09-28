package data

import data.Note.Type.TEXT
import kotlinx.serialization.Serializable

@Serializable
data class Note(
    val id: Long = 0L,
    val title: String,
    val description: String = "default description",
    val type: Type = TEXT
) {
    enum class Type { TEXT, AUDIO }
}
