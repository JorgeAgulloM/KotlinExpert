package extras

private class MyInlineTest() {

    fun test() {
        printObj1(23)
        printObj2(23)
        printObj3 { 23 }
    }

    fun printObj1(obj: Any) {
        println(obj)
    }

    inline fun printObj2(obj: Any) { // símil de la anterior, añadir inline no aporta prácticametne nada
        println(obj)
    }

    inline fun printObj3(f: () -> Any) {
        println(f())
    }

    fun example() {
        listOf(1, 3, 4, 5, 6, 8, 10).filter2 { it % 2 == 0 }
        listOf(1, 3, 4, 5, 6, 8, 10).filter3({ it % 2 == 0; return }, { it % 2 == 0 })
        listOf(1, 3, 4, 5, 6, 8, 10).filter4({ it % 2 == 0 }, { it % 2 == 0 })
        listOf(1, 3, 4, 5, 6, 8, 10).filter5 { it % 2 == 0 }
    }

    inline fun List<Int>.filter2(predicate: (Int) -> Boolean): List<Int> {
        val list = mutableListOf<Int>()
        for (i in this) {
            if (predicate(i)) {
                list.add(i)
            }
        }
        return list
    }

    // Añadir noinlie en uno de los parámetros
    inline fun List<Int>.filter3(predicate: (Int) -> Boolean, noinline predicate2: (Int) -> Boolean): List<Int> {
        val list = mutableListOf<Int>()
        for (i in this) {
            if (predicate(i) && predicate2(i)) {
                list.add(i)
            }
        }
        f(predicate2) // Usar de esta forma el parámetro es posible gracias a declararlo noinline.
        return list
    }

    fun f(predicate: (Int) -> Boolean) {}

    // Añadir crossinline en uno de los parámetros para permitir usar este en un contexto diferente
    inline fun List<Int>.filter4(crossinline predicate: (Int) -> Boolean, predicate2: (Int) -> Boolean): List<Int> {
        val list = mutableListOf<Int>()
        for (i in this) {
            if (predicate(i) && predicate2(i)) {
                list.add(i)
            }
        }

        val obj = Runnable { predicate(20) }
        return list
    }

    // Genérica
    inline fun <T> List<T>.filter5(predicate: (T) -> Boolean): List<T> {
        val list = mutableListOf<T>()
        for (i in this) {
            if (predicate(i)) {
                list.add(i)
            }
        }

        return list
    }

}

private fun main() {
    val myInlineTest = MyInlineTest()
    myInlineTest.test()
}