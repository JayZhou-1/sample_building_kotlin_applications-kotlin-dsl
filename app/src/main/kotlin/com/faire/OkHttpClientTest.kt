package com.faire

import javax.inject.Inject
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody

//class OkHttpClientTest @Inject constructor( @WithTransactionInterceptor val okHttpClientWithInterceptor: OkHttpClient){
class OkHttpClientTest {

    @Inject
    @field:WithTransactionInterceptor
    private lateinit var okHttpClientWithInterceptor: OkHttpClient

//    @Inject
//    @field:WithTransactionInterceptor2
//    private lateinit var okHttpClientWithInterceptor2: OkHttpClient

    @Inject
    private lateinit var okHttpClient: OkHttpClient

    fun callSlack() {
        val slackToken = System.getenv("CODE_CLEANUP_BOT_SLACK_TOKEN")

        val formBody: RequestBody = FormBody.Builder()
            .add("channel", "#testing")
            .add("text", "jayMessage from raw ${this.javaClass}")
            .build()
        val request = Request.Builder()
            .url("https://slack.com/api/chat.postMessage")
            .addHeader("Authorization", "Bearer $slackToken")
            .post(formBody)
            .build()

        val call = okHttpClient.newCall(request);
        val response = call.execute();
        println("response = ${response}")
        okHttpClient.connectionPool.evictAll()
    }

    fun callGoogleWithoutOkHttpClientInterceptor() {
        val request = Request.Builder()
            .url("https://google.com")
            .build()

        val call = okHttpClient.newCall(request);
        val response = call.execute();
        println("response = ${response}")
//        okHttpClient.connectionPool.evictAll()
    }

    fun callGoogleWithOkHttpClientInterceptor() {
        val request = Request.Builder()
            .url("https://google.com")
            .build()

        val call = okHttpClientWithInterceptor.newCall(request);
        val response = call.execute();
        println("response = ${response}")
//        okHttpClientWithInterceptor.connectionPool.evictAll()
    }

}