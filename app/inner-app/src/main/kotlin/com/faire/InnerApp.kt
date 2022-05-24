package com.faire

import faire.BackFill
import faire.NotifyAfter

@NotifyAfter("2022-07-04")
class InnerApp : BackFill(){

}

fun main() {
    println("hello world from InnerApp")
}