package extras

sealed class CanWalk(val legs: Int) {
    class Elephant(val name: String) : CanWalk(4)
    class Spider(val age: Int) : CanWalk(8)
}

sealed interface CanFly

data object Swan : CanWalk(2), CanFly
data object Fly : CanFly

fun testSealed(canWalk: CanWalk): Int = when (canWalk) {
    is CanWalk.Elephant -> canWalk.name.toInt()
    is CanWalk.Spider -> canWalk.age
    Swan -> canWalk.legs
}

fun test2Sealed(canFly: CanFly): Int = when (canFly) {
    Fly -> TODO()
    Swan -> TODO()
}

val elephant = CanWalk.Elephant("Bimbo")