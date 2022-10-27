package com.faire

import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.s3.model.ListObjectsV2Request

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
}
fun main(){
    val s3Test = S3Test()
    s3Test.getObjects()
}