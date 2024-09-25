package extras

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

// Lleva mucho tiempo en experimental, por lo que es posible que desaparezca. No usar si no es totalmente necesario.

data class Person(val name: String, val age: Int)

fun firstLatter(person: Person): Char {
    val firstLetter: Char
    person.name.let { firstLetter = it[0] } // Let funciona con contracts configurando la llamada al scope de exactamente una vez

    return firstLetter
}

@OptIn(ExperimentalContracts::class)
fun String?.hasChildren(): Boolean {
    contract {
        returns(true) implies (this@hasChildren is String)
    }
    return (this is String)
}


fun printChildrenCount(string: String) {
    if (string.hasChildren()) {
        println(string.length)
    }
}


fun main() {
    val name: String? = ""

    if (!name.isNullOrEmpty()) {
        name.reversed()
    }

    printChildrenCount("Hello World!")


}
