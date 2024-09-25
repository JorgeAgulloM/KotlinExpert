@file:JvmName("Utils")

package extras.javaCompat

private fun main() {
    /*    val javaTest = JavaString()
        val str: String =
            javaTest.myStringIndeterminateNullable //== String! !!!Si no estamos seguros de que esto se vaya a cumplir, debemos tratarla como nulable
        val str2: String? = javaTest.myStringNullable // (Implementada con la etiqueta @Nullable de java)
        val str3: String = javaTest.myStringNotNullable // (Implementada con la etiqueta @NotNull de java)

        val obj = JavaCallback { }

        val obj2 = object : JavaCallbackTwo {
            override fun execute(str: String?) {

            }

            override fun delete(str: String?) {

            }
        }*/

    cosa()
}

fun myTopLevelFunction(str: String) {

}

fun String.myTopLevelFunctionExt(str: String): String {
    return "$this and $str"
}

@JvmOverloads
fun myTopLevelFunctionStr(str1: String, str2: String = "World") {
    println(str1 + str2)
}

fun myTopLevelFunctionLambda(lambda: (String, Int) -> String) {
    println(lambda.invoke("Hello", 23))
}

fun cosa() {
    myTopLevelFunctionLambda { str, num -> "$str tiene $num de cosas" }
}

class MyKotlinClassTest() {
    var property: String = ""
}
