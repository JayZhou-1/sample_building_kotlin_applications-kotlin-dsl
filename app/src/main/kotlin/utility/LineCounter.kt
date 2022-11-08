package utility

import java.io.File

class LineCounter {
}

fun main() {
//    val dir = "/Users/shuaizhou/Documents/test"
    val dir = "/Users/shuaizhou/Documents/changefeed1"
    var count = 0L
    File(dir).walk().forEach { file ->
        if (!file.name.contains("DS_Store") && !file.isDirectory) {
            file.forEachLine {
                count++
            }
        }
    }
    println("count: $count")
}