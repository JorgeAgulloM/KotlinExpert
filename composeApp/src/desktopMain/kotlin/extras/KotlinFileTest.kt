package extras

import kotlin.properties.Delegates

fun main() {
    //Collections
    data class Note(val title: String, val description: String)

    val list: List<Note> = listOf(
        Note("Title 1", "Description 1"),
        Note("Title 2", "Description 2"),
        Note("Title 2", "Description 2"),
        Note("Title 3", "Description 3"),
        Note("Title 3", "Description 3"),
        Note("Title 4", "Description 4"),
        Note("Title 5", "Description 5"),
        Note("Title 5", "Description 5"),
        Note("Title 6", "Description 6")
    )

//Immutable default
    val emptyList: List<Note> = emptyList()
//emptyList.add X

//Mal rendimiento
    val list2 = list + Note("Title 7", "Description 7")
    println(list2)

    val mutableList: MutableList<Note> = mutableListOf()
    mutableList.add(Note("Title 8", "Description 8"))
    println(mutableList)

//Set

    //Si hay elementos repetidos no se usan
    val set: Set<Note> = setOf(
        Note("Title 1", "Description 1"),
        Note("Title 2", "Description 2"),
        Note("Title 2", "Description 2"),
        Note("Title 3", "Description 3"),
        Note("Title 3", "Description 3"),
        Note("Title 4", "Description 4"),
        Note("Title 5", "Description 5"),
        Note("Title 5", "Description 5"),
        Note("Title 6", "Description 6")
    )
    println(set)

//Map
    val map: Map<Int, Note> = mapOf(
        Pair(1, Note("Title 1", "Description 1")),
        Pair(2, Note("Title 2", "Description 2")),
        Pair(3, Note("Title 3", "Description 3")),
        Pair(4, Note("Title 4", "Description 4")),
        Pair(5, Note("Title 5", "Description 5")),
        Pair(6, Note("Title 6", "Description 6"))
    )

    val map2: Map<Int, Note> = mapOf(
        1 to Note("Title 1", "Description 1"),
        2 to Note("Title 2", "Description 2"),
        3 to Note("Title 3", "Description 3"),
        4 to Note("Title 4", "Description 4"),
        5 to Note("Title 5", "Description 5"),
        6 to Note("Title 6", "Description 6")
    )


//Operadores funcionales
    println(list.filter { !it.title.contains("4") })
    println(list.filterNot { it.title.contains("4") })
    println(list.filterNot { it.title.contains("4") }.map { it.title }.sorted())
    println(list.filterNot { it.title.contains("4") }.sortedBy { it.description }.map { it.title })

//Enums
    val myShape: Shape = Shape.RECTANGLE
    println(myShape.ordinal)
    println(Shape.valueOf("SQUARE"))

    val myShapeWithProperties = ShapeWithValues.TRIANGLE
    println(myShapeWithProperties.id)
    println(myShapeWithProperties.shapeName)

}

enum class Shape {
    SQUARE,
    RECTANGLE,
    CIRCLE,
    OVAL,
    TRIANGLE
}


enum class ShapeWithValues(val id: Int, val shapeName: String) {
    SQUARE(1, "Square"),
    RECTANGLE(2, "Rectangle"),
    CIRCLE(3, "Circle"),
    OVAL(4, "Oval"),
    TRIANGLE(5, "Triangle")
}

//Objects
object Database {

    private val items = mutableListOf<Int>()

    fun getAll(): List<Int> = items

    fun save(value: Int) {
        items.add(value)
    }
}

fun thingOne() {
    Database.save(2)
    Database.save(3)
    Database.getAll()
}

fun thing() {
    val obj = object {
        val x = 20
        val y = "Hello"
    }

    obj.x
}

interface Callback {
    fun invoke()
}

fun foo(callback: Callback) {
    //
}

fun thing2() {
    foo(object : Callback {
        override fun invoke() {
            /*Cosas que hacen cosas*/
        }
    })
}

class Thing {

    companion object MyCompanionObject { /** Se puede modificar el nombre del companion **/
    var x = 20
        fun foo(): String {
            return "Cosas que hacen cosas"
        }
    }

}

fun thingTwo() {
    Thing.foo()
    Thing.foo()
}

//Delegación de propiedades

//Lazy
class Database2 {
    fun save() {}
}

class MyClass {
    private val db by lazy { Database2() }

    fun save() {
        db.save()
    }
}

val obj = MyClass()

//All llamar la primera vez al objeto, se crea la instancia
fun thingThree() {
    obj.save()
}

//Observable
class MyClass2 {
    var x: Int by Delegates.observable(0) { _, oldValue, newValue ->
        println("oldValue: $oldValue, newValue: $newValue")
    }
}

val obj2 = MyClass2()
fun thingFour() {
    obj2.x = 4
    obj2.x = 6
}

//Vetoable
class MyClass3 {
    var positiveInt: Int by Delegates.vetoable(0) { _, _, newValue ->
        newValue >= 0
    }
}

val obj3 = MyClass3()
fun thingFive() {
    obj3.positiveInt = 3
    obj3.positiveInt
    obj3.positiveInt = -6
    obj3.positiveInt
    obj3.positiveInt = 10
    obj3.positiveInt
}

// Late initialization
class MyClass4 {
    private lateinit var lateInitialization: String

    private var lateInitializationNull: String? = null

    private var lateInitializationBasic: Int by Delegates.notNull()

    init {
        if (!::lateInitialization.isInitialized) {
            lateInitialization = "Hello World!"
        }

        if (lateInitializationNull == null) {
            lateInitializationNull = "Hello World!"
        }

        lateInitializationBasic = 0
    }
}
