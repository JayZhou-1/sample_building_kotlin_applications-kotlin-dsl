package com.faire

import faire.BackFill
import faire.NeverNotify

@NeverNotify
class InnerApp : BackFill(){

}

fun main() {
    println("hello world from InnerApp")
}