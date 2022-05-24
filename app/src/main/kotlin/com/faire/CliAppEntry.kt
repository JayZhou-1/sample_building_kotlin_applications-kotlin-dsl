package com.faire

class CliAppEntry {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            println("args = ${args.joinToString { it }}")
//                    val reflection = Reflections("com.faire")
//                    val classSet = reflection.getSubTypesOf(Backfill::class.java)
//                    println(classSet.size)
//                    classSet.forEach {
//                        println(it.canonicalName)
//                        println("it.annotations.size: ${it.annotations.size}")
//                        try {
//                            val productArea = it.annotations.singleOrNull {
//                                it is TagProductArea
//                            }!! as TagProductArea
//                            println("product area: ${productArea.area}")
//                        } catch (e: Exception) {
//                            println("================== + $it")
//                        }
//                    }
        }
    }
}