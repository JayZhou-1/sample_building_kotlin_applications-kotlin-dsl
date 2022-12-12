package com.faire

import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.s3.model.GetObjectRequest
import com.amazonaws.services.s3.model.ListObjectsV2Request
import com.amazonaws.services.s3.model.S3Object

class S3Test {
    val s3Client = AmazonS3ClientBuilder
        .standard()
        .withRegion(Regions.fromName("us-east-1"))
        .enablePathStyleAccess()
        .build()

    fun getObjects() {
        val request = ListObjectsV2Request()
            .withBucketName("faire-us-east-1-staging-cockroachdb")
            .withPrefix("staging-email-sender/changefeeds/test/")
        val response = s3Client.listObjectsV2(request)
        response.objectSummaries.forEach { s3ObjectSummary ->
            println("s3ObjectSummary.key: ${s3ObjectSummary.key}")
        }
    }

    fun <T> getObject(bucketName: String, key: String, block: (Sequence<String>) -> T): T {
        val response: S3Object = s3Client.getObject(bucketName, key)
        return response.objectContent
            .let { GzipUtils.maybeDecompress(it) }
            .bufferedReader()
            .useLines(block)
    }

    fun getLines(keyList: List<String>): Int {
        var count = 0
        val bucketName = "faire-us-east-1-staging-cockroachdb"
        for (key in keyList) {
            count += getObject(bucketName, key) {
                it.count()
            }
        }
        println("count: $count")
        return count
    }

}

fun main() {
    val s3Test = S3Test()
//    s3Test.getObjects()


    val s3KeysAfterSetCpu2 = listOf(
        "staging-email-sender/changefeeds/test/2022-11-15/202211151257523834392450000000001-b16e3c956b71db5b-1-12-0000006a-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-15/202211151257586170354600000000001-eb7344820baa8d31-3-8-00000067-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-15/202211151258226706857510000000001-b16e3c956b71db5b-1-12-0000006b-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-15/202211151258277998174990000000001-eb7344820baa8d31-3-8-00000068-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-15/202211151258532613300140000000001-b16e3c956b71db5b-1-12-0000006c-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-15/202211151258588394703520000000001-eb7344820baa8d31-3-8-00000069-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-15/202211151259237893799020000000001-b16e3c956b71db5b-1-12-0000006d-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-15/202211151259298331091660000000001-eb7344820baa8d31-3-8-0000006a-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-15/202211151259548628690100000000001-b16e3c956b71db5b-1-12-0000006e-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-15/202211151300008605452790000000001-eb7344820baa8d31-3-8-0000006b-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-15/202211151300259019545390000000001-b16e3c956b71db5b-1-12-0000006f-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-15/202211151300318716421960000000001-eb7344820baa8d31-3-8-0000006c-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-15/202211151300569505490580000000001-b16e3c956b71db5b-1-12-00000070-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-15/202211151301028743938180000000001-eb7344820baa8d31-3-8-0000006d-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-15/202211151301280526393520000000001-b16e3c956b71db5b-1-12-00000071-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-15/202211151301339052590840000000001-eb7344820baa8d31-3-8-0000006e-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-15/202211151301590917008070000000001-b16e3c956b71db5b-1-12-00000072-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-15/202211151302049212389280000000001-eb7344820baa8d31-3-8-0000006f-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-15/202211151302301487160470000000001-b16e3c956b71db5b-1-12-00000073-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-15/202211151302359239204730000000001-eb7344820baa8d31-3-8-00000070-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-15/202211151303012092297990000000001-b16e3c956b71db5b-1-12-00000074-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-15/202211151303069646765930000000001-eb7344820baa8d31-3-8-00000071-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-15/202211151303322829100700000000001-b16e3c956b71db5b-1-12-00000075-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-15/202211151303380480679330000000001-eb7344820baa8d31-3-8-00000072-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-15/202211151304033297930690000000001-b16e3c956b71db5b-1-12-00000076-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-15/202211151304090358049500000000001-eb7344820baa8d31-3-8-00000073-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-15/202211151304344207969330000000001-b16e3c956b71db5b-1-12-00000077-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-15/202211151304400560429750000000001-eb7344820baa8d31-3-8-00000074-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-15/202211151305054286111600000000001-b16e3c956b71db5b-1-12-00000078-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-15/202211151305110844043780000000001-eb7344820baa8d31-3-8-00000075-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-15/202211151305364671464420000000001-b16e3c956b71db5b-1-12-00000079-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-15/202211151305420914914510000000001-eb7344820baa8d31-3-8-00000076-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-15/202211151306075118743530000000001-b16e3c956b71db5b-1-12-0000007a-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-15/202211151306131045283390000000001-eb7344820baa8d31-3-8-00000077-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-15/202211151306385574573960000000001-b16e3c956b71db5b-1-12-0000007b-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-15/202211151306441275466150000000001-eb7344820baa8d31-3-8-00000078-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-15/202211151307096456244030000000001-b16e3c956b71db5b-1-12-0000007c-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-15/202211151307152066946730000000001-eb7344820baa8d31-3-8-00000079-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-15/202211151307406863331870000000001-b16e3c956b71db5b-1-12-0000007d-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-15/202211151307461612609970000000001-eb7344820baa8d31-3-8-0000007a-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-15/202211151308117852891450000000001-b16e3c956b71db5b-1-12-0000007e-bank-1.ndjson.gz",
    )
    s3Test.getLines(s3KeysAfterSetCpu2)
}
