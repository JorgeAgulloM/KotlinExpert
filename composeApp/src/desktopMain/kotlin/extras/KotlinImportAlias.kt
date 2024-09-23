package extras

import data.Note as DataNote
import extras.Note as ExtrasNote

fun ExtrasNote.toData(): DataNote = DataNote(title, description)
