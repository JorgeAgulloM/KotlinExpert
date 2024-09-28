package extras

import data.Note as DataNote
import extras.Note as ExtrasNote

typealias DataNoteTyped = data.Note //Estos son públicos para toda la app, pero no sustituye al original, se pueden usar indistintamente
private typealias ExtraNoteTyped = extras.Note // Tambien se puede hacer privada

//fun ExtrasNote.toData(): DataNote = DataNote(id, title, description)

//fun ExtraNoteTyped.toDataTyped(): DataNoteTyped = DataNoteTyped(title, description)
