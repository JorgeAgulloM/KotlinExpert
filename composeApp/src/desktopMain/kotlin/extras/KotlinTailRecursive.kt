package extras

class KotlinTailRecursive {
    tailrec fun recursiveFactorial(n: Long, accumulator: Long = 1): Long {
        val soFar = n * accumulator
        return if (n <= 1) {
            soFar
        } else {
            recursiveFactorial(n - 1, soFar)
        }
    }
}

fun main() {
    val tailrec = KotlinTailRecursive()
    val result = tailrec.recursiveFactorial(50)
    println(result)
}
