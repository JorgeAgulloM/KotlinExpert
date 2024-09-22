package extras

fun a() {
    val x = 20
    fun local(arg: Int) = x + arg

    val b = local(3)
    val c = local(4)
}
