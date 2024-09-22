package extras

val pair: Pair<String, Int> = Pair("a", 10)

// Ejemplo
val pair2 = "a" to 10

infix fun String.eats(meal: String) = Lunch(this, meal)

class Lunch(val name: String, val meal: String)

val lunch = Lunch("Tom", "Pizza")

val lunch2 = "Tom" eats "Pizza"

fun main(): Unit {
    println(lunch.toString())
    println(lunch2.toString())
}
