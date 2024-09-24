package extras

private class KotlinReifiedTypes {
    fun list1() {
        listOf(20, "string", 20f, 5, 8L, 20.0, "String2").filterIsInstance<Int>() //=> [20, 5]
        listOf(20, "string", 20f, 5, 8L, 20.0, "String2").filterIsInstance<String>() //=> [String, String2
        listOf(20, "string", 20f, 5, 8L, 20.0, "String2").filterIsInstance1<String>() //=> [String, String2
    }

    inline fun <reified T> List<Any>.filterIsInstance1(): List<T> {
        val res = mutableListOf<T>()
        for (item in this) {
            if (item is T) res.add(item) // Si no usamos inline + reified, no podríamos acceder al tipo de T dentro de la función
        }
        return res
    }
}