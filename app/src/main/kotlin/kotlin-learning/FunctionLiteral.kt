package `kotlin-learning`

object FunctionLiteral {

    fun <K, V> buildMutableMap(build: HashMap<K, V>.() -> Unit): Map<K, V> {
        val map = HashMap<K, V>()
        map.build()
        return map
    }

    fun usage(): Map<Int, String> {
        return buildMutableMap {
            put(0, "0")
            for (i in 1..10) {
                put(i, "$i")
            }
        }
    }

}

fun main() {
    val map = FunctionLiteral.usage()
    println("map = ${map}")
}