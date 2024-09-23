package extras

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.sql.Blob
import javax.sound.midi.Receiver


private fun myTestLambdas() {

    val f: (Int, Int) -> Int = { x, y -> x + y }
    f(4, 5)

    val f2 = { x: Int, y: Int -> x + y }
    f2(4, 5)

    fun doOp(x: Int, y: Int, op: (Int, Int) -> Int) = op(x, y)
    doOp(4, 5, f)
    doOp(4, 5, { x, y -> x * y })
    doOp(4, 5) {
        /*Add code*/
            x, y ->
        x * y
    }

    val f3: (Int) -> Int = { it * it }
    f3(5)

    fun foo(f: String.() -> String) {}

    fun String.toUpper() = uppercase()

    "Hello".toUpper()
    foo {
        uppercase()
    }

    fun myWithFunction() {
        fun with1(receiver: String, block: (String) -> Unit) {}
        fun with2(receiver: String, block: String.() -> Unit) {
            receiver.block()
        }

        fun <T> with3(receiver: T, block: T.() -> Unit) {
            receiver.block()
        }

        fun <T> with4(receiver: T, block: T.() -> Int): Int = receiver.block()
        fun <T, R> with5(receiver: T, block: T.() -> R): R = receiver.block()
        /** Símil a la función de scope With **/

        with1("Hello") {
            println(it.uppercase())
        }
        with2("Hello") {
            println(this.uppercase())
        }
        with3("Hello") {
            println(this.uppercase())
        }
        val x = with4(24) {
            this.also(::println)
        }
        val x2 = with5(24) {
            this.also(::println)
        }
    }

    fun myRunFunction() {
        fun String.run1(block: String.() -> Int): Int = block()
        fun <T, R> T.run2(block: T.() -> R): R = block()

        "Hello".run1 {
            println(this.uppercase())
            10
        }

        21.run2 {
            println(this)
            10
        }
    }

    fun myApplyFunction() {
        fun String.apply1(block: String.() -> Unit): String {
            this.block()
            return this
        }

        fun <T> T.apply2(block: T.() -> Unit): T {
            this.block()
            return this
        }

        "Hello".apply1 {
            println(this.uppercase())
        }
        "Hello".apply2 {
            println(this.uppercase())
        }
    }

    fun myAlsoFunction() {
        fun String.also1(block: (String) -> Unit): String {
            block(this)
            return this
        }

        fun <T> T.also2(block: (T) -> Unit): T {
            block(this)
            return this
        }


        "Hello".also1 {
            println(it.uppercase())
        }
        "Hello".also2 {
            println(it.uppercase())
        }
    }

    fun myLetFunction() {
        fun String.let1(block: (String) -> Int): Int = block(this)
        fun <T, R> T.let2(block: (T) -> R): R = block(this)

        "Hello".let1 {
            println(it.uppercase())
            29
        }
        "Hello".let2 {
            println(it.uppercase())
            29
        }
    }
}
