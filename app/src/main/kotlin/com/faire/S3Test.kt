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

    val s3Keys = listOf(
        "staging-email-sender/changefeeds/test/2022-11-08/202211081641381706284850000000001-b16e3c956b71db5b-1-12-00000007-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211081641489013648220000000001-eb7344820baa8d31-3-8-00000007-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211081642075948487340000000001-b16e3c956b71db5b-1-12-00000008-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211081642188810930160000000001-eb7344820baa8d31-3-8-00000008-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211081642386851304650000000001-b16e3c956b71db5b-1-12-00000009-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211081642498897245620000000001-eb7344820baa8d31-3-8-00000009-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211081643097223412220000000001-b16e3c956b71db5b-1-12-0000000a-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211081643209008909550000000001-eb7344820baa8d31-3-8-0000000a-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211081643407600571240000000001-b16e3c956b71db5b-1-12-0000000b-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211081643519312334530000000001-eb7344820baa8d31-3-8-0000000b-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211081644118541186230000000001-b16e3c956b71db5b-1-12-0000000c-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211081644229757681400000000001-eb7344820baa8d31-3-8-0000000c-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211081644428923556330000000001-b16e3c956b71db5b-1-12-0000000d-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211081644539924551470000000001-eb7344820baa8d31-3-8-0000000d-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211081645140690403900000000001-b16e3c956b71db5b-1-12-0000000e-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211081645250108397640000000001-eb7344820baa8d31-3-8-0000000e-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211081645451439013180000000001-b16e3c956b71db5b-1-12-0000000f-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211081645560246216180000000001-eb7344820baa8d31-3-8-0000000f-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211081646161851565760000000001-b16e3c956b71db5b-1-12-00000010-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211081646270510763540000000001-eb7344820baa8d31-3-8-00000010-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211081646472676060140000000001-b16e3c956b71db5b-1-12-00000011-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211081646580847012080000000001-eb7344820baa8d31-3-8-00000011-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211081647183131738250000000001-b16e3c956b71db5b-1-12-00000012-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211081647290892775910000000001-eb7344820baa8d31-3-8-00000012-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211081647493342806620000000001-b16e3c956b71db5b-1-12-00000013-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211081648001435965790000000001-eb7344820baa8d31-3-8-00000013-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211081648204643098570000000001-b16e3c956b71db5b-1-12-00000014-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211081648311683588320000000001-eb7344820baa8d31-3-8-00000014-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211081648515476078270000000001-b16e3c956b71db5b-1-12-00000015-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211081649022009230650000000001-eb7344820baa8d31-3-8-00000015-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211081649226681690970000000001-b16e3c956b71db5b-1-12-00000016-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211081649332193151290000000001-eb7344820baa8d31-3-8-00000016-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211081649537637994920000000001-b16e3c956b71db5b-1-12-00000017-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211081650042450849740000000001-eb7344820baa8d31-3-8-00000017-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211081650247848739720000000001-b16e3c956b71db5b-1-12-00000018-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211081650352728072200000000001-eb7344820baa8d31-3-8-00000018-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211081650558479100670000000001-b16e3c956b71db5b-1-12-00000019-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211081651062931870220000000001-eb7344820baa8d31-3-8-00000019-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211081651268934137350000000001-b16e3c956b71db5b-1-12-0000001a-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211081651372918225250000000001-eb7344820baa8d31-3-8-0000001a-bank-1.ndjson.gz",
    )
//    s3Test.getLines(s3Keys)

    val s3KeysAfterSetCpu = listOf(
        "staging-email-sender/changefeeds/test/2022-11-08/202211082259129293117610000000001-b16e3c956b71db5b-1-12-0000001b-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211082259259190852780000000001-eb7344820baa8d31-3-8-0000001b-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211082259425487100760000000001-b16e3c956b71db5b-1-12-0000001c-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211082259558288696200000000001-eb7344820baa8d31-3-8-0000001c-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211082300126905105020000000001-b16e3c956b71db5b-1-12-0000001d-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211082300268513274510000000001-eb7344820baa8d31-3-8-0000001d-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211082300437302056680000000001-b16e3c956b71db5b-1-12-0000001e-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211082300578743119710000000001-eb7344820baa8d31-3-8-0000001e-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211082301137959843340000000001-b16e3c956b71db5b-1-12-0000001f-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211082301288899177090000000001-eb7344820baa8d31-3-8-0000001f-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211082301448288188440000000001-b16e3c956b71db5b-1-12-00000020-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211082301599097137480000000001-eb7344820baa8d31-3-8-00000020-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211082302159092763640000000001-b16e3c956b71db5b-1-12-00000021-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211082302309549202520000000001-eb7344820baa8d31-3-8-00000021-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211082302469495789090000000001-b16e3c956b71db5b-1-12-00000022-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211082303019714413560000000001-eb7344820baa8d31-3-8-00000022-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211082303180321226190000000001-b16e3c956b71db5b-1-12-00000023-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211082303329769779130000000001-eb7344820baa8d31-3-8-00000023-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211082303485119334020000000001-b16e3c956b71db5b-1-12-00000024-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211082304040462557580000000001-eb7344820baa8d31-3-8-00000024-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211082304192824969490000000001-b16e3c956b71db5b-1-12-00000025-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211082304350195689620000000001-eb7344820baa8d31-3-8-00000025-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211082304503455665820000000001-b16e3c956b71db5b-1-12-00000026-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211082305060361589540000000001-eb7344820baa8d31-3-8-00000026-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211082305214000056830000000001-b16e3c956b71db5b-1-12-00000027-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211082305370561346360000000001-eb7344820baa8d31-3-8-00000027-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211082305524967097630000000001-b16e3c956b71db5b-1-12-00000028-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211082306080948692830000000001-eb7344820baa8d31-3-8-00000028-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211082306235612703370000000001-b16e3c956b71db5b-1-12-00000029-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211082306391076580540000000001-eb7344820baa8d31-3-8-00000029-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211082306546359354010000000001-b16e3c956b71db5b-1-12-0000002a-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211082307101380382200000000001-eb7344820baa8d31-3-8-0000002a-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211082307256601536020000000001-b16e3c956b71db5b-1-12-0000002b-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211082307411854507900000000001-eb7344820baa8d31-3-8-0000002b-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211082307567936050950000000001-b16e3c956b71db5b-1-12-0000002c-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211082308121682840460000000001-eb7344820baa8d31-3-8-0000002c-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211082308278501552680000000001-b16e3c956b71db5b-1-12-0000002d-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211082308431940061950000000001-eb7344820baa8d31-3-8-0000002d-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211082308589002378450000000001-b16e3c956b71db5b-1-12-0000002e-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211082309134058540620000000001-eb7344820baa8d31-3-8-0000002e-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-08/202211082309299953282250000000001-b16e3c956b71db5b-1-12-0000002f-bank-1.ndjson.gz",
        )
//    s3Test.getLines(s3KeysAfterSetCpu)

    val s3KeysAfterSetCpu2 = listOf("staging-email-sender/changefeeds/test/2022-11-09/202211091315497126137640000000001-b16e3c956b71db5b-1-12-00000030-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-09/202211091316040784159540000000001-eb7344820baa8d31-3-8-0000002f-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-09/202211091316189642334170000000001-b16e3c956b71db5b-1-12-00000031-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-09/202211091316337653484000000000001-eb7344820baa8d31-3-8-00000030-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-09/202211091316499869371010000000001-b16e3c956b71db5b-1-12-00000032-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-09/202211091317047797702400000000001-eb7344820baa8d31-3-8-00000031-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-09/202211091317210773919420000000001-b16e3c956b71db5b-1-12-00000033-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-09/202211091317358051590890000000001-eb7344820baa8d31-3-8-00000032-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-09/202211091317521208359070000000001-b16e3c956b71db5b-1-12-00000034-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-09/202211091318068270482970000000001-eb7344820baa8d31-3-8-00000033-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-09/202211091318231650044600000000001-b16e3c956b71db5b-1-12-00000035-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-09/202211091318378513784550000000001-eb7344820baa8d31-3-8-00000034-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-09/202211091318542467631940000000001-b16e3c956b71db5b-1-12-00000036-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-09/202211091319088722670000000000001-eb7344820baa8d31-3-8-00000035-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-09/202211091319252827380840000000001-b16e3c956b71db5b-1-12-00000037-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-09/202211091319398807248860000000001-eb7344820baa8d31-3-8-00000036-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-09/202211091319563346345620000000001-b16e3c956b71db5b-1-12-00000038-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-09/202211091320109274792510000000001-eb7344820baa8d31-3-8-00000037-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-09/202211091320274126469900000000001-b16e3c956b71db5b-1-12-00000039-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-09/202211091320419327953620000000001-eb7344820baa8d31-3-8-00000038-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-09/202211091320584949559720000000001-b16e3c956b71db5b-1-12-0000003a-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-09/202211091321129432678590000000001-eb7344820baa8d31-3-8-00000039-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-09/202211091321295684430590000000001-b16e3c956b71db5b-1-12-0000003b-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-09/202211091321439760067500000000001-eb7344820baa8d31-3-8-0000003a-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-09/202211091322006094643650000000001-b16e3c956b71db5b-1-12-0000003c-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-09/202211091322149921149410000000001-eb7344820baa8d31-3-8-0000003b-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-09/202211091322317003701600000000001-b16e3c956b71db5b-1-12-0000003d-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-09/202211091322460020100980000000001-eb7344820baa8d31-3-8-0000003c-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-09/202211091323027038077680000000001-b16e3c956b71db5b-1-12-0000003e-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-09/202211091323170336806460000000001-eb7344820baa8d31-3-8-0000003d-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-09/202211091323333759772870000000001-b16e3c956b71db5b-1-12-0000003f-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-09/202211091323480513569110000000001-eb7344820baa8d31-3-8-0000003e-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-09/202211091324039040859980000000001-b16e3c956b71db5b-1-12-00000040-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-09/202211091324190789447390000000001-eb7344820baa8d31-3-8-0000003f-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-09/202211091324349779180000000000001-b16e3c956b71db5b-1-12-00000041-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-09/202211091324500904883540000000001-eb7344820baa8d31-3-8-00000040-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-09/202211091325060318321600000000001-b16e3c956b71db5b-1-12-00000042-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-09/202211091325211234304550000000001-eb7344820baa8d31-3-8-00000041-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-09/202211091325371145736770000000001-b16e3c956b71db5b-1-12-00000043-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-09/202211091325521433345930000000001-eb7344820baa8d31-3-8-00000042-bank-1.ndjson.gz",
        "staging-email-sender/changefeeds/test/2022-11-09/202211091326081821914270000000001-b16e3c956b71db5b-1-12-00000044-bank-1.ndjson.gz",
    )
    s3Test.getLines(s3KeysAfterSetCpu2)
}
