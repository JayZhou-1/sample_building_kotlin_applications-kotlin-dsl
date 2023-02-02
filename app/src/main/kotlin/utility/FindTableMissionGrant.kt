package utility

import com.google.gson.Gson

class FindTableMissionGrant {

}
class Foo {
    var intValue = 0
    var stringValue: String? = null // + standard equals and hashCode implementations
}
fun main() {
    val gson = Gson()
    val showTableResultList = gson.fromJson(showTableResultArray, Array<ShowTable>::class.java)
    println("showTableResultList: ${showTableResultList.size}")
    println("showTableResultList: ${showTableResultList[0].type}")


    val showTableResultObject = gson.fromJson(showTableResult, ShowTableResult::class.java)
    println("showTableResultObject: $showTableResultObject")
    println(showTableResultObject.`show tables`.size)
    val tableNames = showTableResultObject.`show tables`.map { it.table_name }.toSet()
    println("tableNames: ${tableNames.size}")

    val tableGrantObject = gson.fromJson(tableGrant, TableGrant::class.java)
    val tableNamesFromGrant = tableGrantObject.`SHOW GRANTS FOR email_sender`.map { it.relation_name }.filterNotNull().toSet()

    println("tableNamesFromGrant: ${tableNamesFromGrant.size}")
    println("tableNamesFromGrant: ${tableNamesFromGrant}")

//    for(tableName in tableNames) {
//        if (!tableNamesFromGrant.contains(tableName)) {
//            println("table $tableName is not in grant")
//        }
//    }
//
//    for(tableName in tableNamesFromGrant) {
//        if (!tableNames.contains(tableName)) {
//            println("table $tableName is not in show table")
//        }
//    }

}

class TableGrant (
    val `SHOW GRANTS FOR email_sender`: List<TableGrantItem>
)

class TableGrantItem (
    val grantee: String,
    val relation_name: String,
)


public class ShowTableResult(
    val `show tables`: List<ShowTable>
)

public class ShowTable(
    val schema_name: String,
    val table_name: String,
    val type: String,
    val owner: String,
    val estimated_row_count: Int,
    val locality: String?
)

val showTableResultArray = """
    [
    	{
    		"schema_name" : "public",
    		"table_name" : "adriel_testing",
    		"type" : "table",
    		"owner" : "owner",
    		"estimated_row_count" : 660000,
    		"locality" : null
    	},
    	{
    		"schema_name" : "public",
    		"table_name" : "allowed_staging_email_addresses",
    		"type" : "table",
    		"owner" : "owner",
    		"estimated_row_count" : 4,
    		"locality" : null
    	}
    ]
""".trimIndent()

val showTableResult = """
{
"show tables": [
	{
		"schema_name" : "public",
		"table_name" : "adriel_testing",
		"type" : "table",
		"owner" : "owner",
		"estimated_row_count" : 660000,
		"locality" : null
	},
	{
		"schema_name" : "public",
		"table_name" : "allowed_staging_email_addresses",
		"type" : "table",
		"owner" : "owner",
		"estimated_row_count" : 4,
		"locality" : null
	}
]}

"""

val tableGrant = """
    {
    "SHOW GRANTS FOR email_sender": [
    	{
    		"database_name" : "email_sender_staging",
    		"schema_name" : null,
    		"relation_name" : null,
    		"grantee" : "email_sender",
    		"privilege_type" : "ZONECONFIG",
    		"is_grantable" : false
    	},
    	{
    		"database_name" : "email_sender_staging",
    		"schema_name" : "public",
    		"relation_name" : "adriel_testing",
    		"grantee" : "email_sender",
    		"privilege_type" : "DELETE",
    		"is_grantable" : false
    	}    	
    ]}
""".trimIndent()