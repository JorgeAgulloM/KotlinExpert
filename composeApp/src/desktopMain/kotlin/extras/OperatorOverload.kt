package extras

/**
 * El operador + equivale a la función de extensión .plus(x), por lo que para sobrecargarla dentro de una misma clase
 * poemos generar una función de extensión de la misma y usar su símbolo de operador.
 * **/
operator fun Note.plus(other: Note): Note = Note(title, "$description ${other.description}", type)

fun testOperators(notes: List<Note>, note2: Note) = notes + note2
