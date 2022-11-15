package utility

import java.io.File

class CsvComparator {

}

fun main() {
    println("hello world")
    val fileName = ""
    val mysqlMap = convertFileToMap("/Users/shuaizhou/Documents/processedFile.csv")
    val crdbMap = convertFileToMap("/Users/shuaizhou/Documents/bank_202211142005.csv")// CRDB
    if (crdbMap == mysqlMap) {
        println("two files are equal")
    } else {
        crdbMap.entries.stream().forEach { entry ->
            val val1 = mysqlMap.get(entry.key)
            val val2 = crdbMap.get(entry.key)
            if (val1 != val2) {
                println("${entry.key} is different for val1: $val1, val2: $val2")
            }

        }
    }
}

private fun convertFileToMap(fileName: String, skipFirstLine: Boolean = true): Map<String, String> {
    val file1 = File(fileName)
    val map1 = mutableMapOf<String, String>()
    var firstLine = skipFirstLine;
    file1.forEachLine { line ->
        if (firstLine) {
            firstLine = false
        } else {
            val id = line.split(",")[0]
            map1.put(id, line)
        }
    }
    return map1
}