package extras

import extras.SpecialNote.Type.AUDIO
import extras.SpecialNote.Type.TEXT
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

data class SpecialNote(val title: String, val description: String = "default", val type: Type = TEXT) {
    enum class Type { TEXT, AUDIO }
    companion object {
        // Dentro de la propia class
        fun getNotes2() = flow {
            delay(2000)
            emit((1..10).map {
                SpecialNote(
                    title = "Title $it",
                    description = "Description $it",
                    type = if (it % 3 == 0) AUDIO else TEXT
                )
            })
        }

        // Ext Propertie detro de la propia class
        val fakeNotes2 get() = flow {
            delay(2000)
            emit((1..10).map {
                SpecialNote(
                    title = "Title $it",
                    description = "Description $it",
                    type = if (it % 3 == 0) AUDIO else TEXT
                )
            })
        }
    }
}

// Fuera de la propia class
fun SpecialNote.Companion.getNotes() = flow {
    delay(2000)
    emit((1..10).map {
        SpecialNote(
            title = "Title $it",
            description = "Description $it",
            type = if (it % 3 == 0) AUDIO else TEXT
        )
    })
}

// Extension properties
val SpecialNote.Companion.fakeNotes get() = flow {
    delay(2000)
    emit((1..10).map {
        SpecialNote(
            title = "Title $it",
            description = "Description $it",
            type = if (it % 3 == 0) AUDIO else TEXT
        )
    })
}

private fun myTestExtCompanion() {
    SpecialNote.getNotes()
    SpecialNote.getNotes2()
    SpecialNote.fakeNotes
    SpecialNote.fakeNotes2
}
