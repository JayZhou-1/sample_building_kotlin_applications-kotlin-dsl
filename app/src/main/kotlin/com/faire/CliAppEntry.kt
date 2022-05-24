package com.faire

import faire.BackFill
import faire.NotifyAfter
import org.reflections.Reflections

class CliAppEntry {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            println("================== CliAppEntry ===========")
            println("args = ${args.joinToString { it }}")

            val reflection = Reflections("com.faire")
            val backfillClass =BackFill::class.java
            val classSet = reflection.getSubTypesOf(backfillClass)
            println(classSet.size)
            classSet.forEach {
                println(it.canonicalName)
                println("it.annotations.size: ${it.annotations.size}")
                try {
                    val notifyAfter = it.annotations.singleOrNull {
                        it is NotifyAfter
                    }!! as NotifyAfter
                    println("product area: ${notifyAfter.date}")
                } catch (e: Exception) {
                    println("================== + $it")
                }
            }
        }
    }
}