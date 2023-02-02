package utility

import java.io.File

class CsvComparator {

}

fun main() {
    println("hello world")
    val fileName = ""
    val mysqlMap = convertFileToMap("/Users/shuaizhou/Documents/processedFile.csv")
    val crdbMap = convertFileToMap("/Users/shuaizhou/Documents/rides_202212121156.csv")// CRDB
    // note: need to config export config 1. change number to have two decimial 2. change datetime to exclude fraction
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
            val fields = line.split(",")
            val fieldsWithQuotes = fields.map {
                if (!it.startsWith("\"")) {
                    "\"$it\""
                } else {
                    it
                }
            }
            val id = fieldsWithQuotes[0]
            val newLine = fieldsWithQuotes.joinToString()
            if (id.startsWith("\"")) {
                map1.put(id, newLine)
            } else {
                map1.put("\"$id\"", newLine)
            }
        }
    }
    return map1
}