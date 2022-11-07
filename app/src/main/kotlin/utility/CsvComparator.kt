package utility

import java.io.File

class CsvComparator {

}

fun main() {
    println("hello world")
    val fileName = ""
    val map1 = convertFileToMap("/Users/shuaizhou/Documents/mySQLResult.csv")
    val map2 = convertFileToMap("/Users/shuaizhou/Documents/bank_202211071618.csv")
    if (map2 == map1) {
        println("two files are equal")
    } else {
        map1.entries.stream().forEach { entry ->

            val val1 = map1.get(entry.key)
            val val2 = map2.get(entry.key)
            if (val1 != val2) {
                println("${entry.key} is different for val1: $val1, val2: $val2")
            }

        }
    }
}

private fun convertFileToMap(fileName: String): Map<String, String> {
    val file1 = File(fileName)
    val map1 = mutableMapOf<String, String>()
    file1.forEachLine { line ->
        val id = line.split(",")[0]
        map1.put(id, line)
    }
    return map1
}