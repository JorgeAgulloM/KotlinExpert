package extras

import androidx.compose.ui.graphics.ColorProducer
import kotlin.reflect.KProperty

private class KotlinPersonalizedDelegates {

    fun test() {

    }

    class Owner {
        var lazyProperty: String by MyLazy { "Hello World!" }
        var lazyPropertyGeneric: Int by MyLazyGeneric { 27 }
        var lazyPropertyGenericByFunction: Int by lazy2 { 20 }

        fun <T> lazy2(producer: () -> T) = MyLazyGeneric(producer)
    }

    class MyLazy(val producer: () -> String) {

        private var lazyObject: String? = null

        // Al declarar Owner en el primer parámetro, este delegado solo podrá usarse en dicha clase
        operator fun getValue(owner: Owner, property: KProperty<*>): String {
            if (lazyObject == null) {
                lazyObject = producer()
            }

            return lazyObject!! //=> Mejor añadir sincronización
        }

        operator fun setValue(owner: Owner, property: KProperty<*>, newValue: String) {
            lazyObject = newValue
        }

        // Al declarar Any en el primer parámetro, este delegado podrá usarse en cualquier clase, si la clase fuese pública
        operator fun getValue(owner: Any, property: KProperty<*>): String {
            if (lazyObject == null) {
                lazyObject = producer()
            }

            return lazyObject!! //=> Mejor añadir sincronización
        }

        operator fun setValue(owner: Any, property: KProperty<*>, newValue: String) {
            lazyObject = newValue
        }
    }

    class MyLazyGeneric<T>(val producer: () -> T) {

        private var lazyGenericObject: T? = null

        // Genérica
        operator fun getValue(owner: Any?, property: KProperty<*>): T {
            if (lazyGenericObject == null) {
                lazyGenericObject = producer()
            }

            return lazyGenericObject!! //=> Mejor añadir sincronización
        }

        operator fun setValue(owner: Any, property: KProperty<*>, newValue: T) {
            lazyGenericObject = newValue
        }

    }
}