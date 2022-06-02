package com.faire

import java.time.Duration
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Test


class NotificationCliEntryTest {

    @Test
    fun `test http service call`(){
        val server = MockWebServer()
        server.start()
        server.enqueue(MockResponse().setHeader("code","300").setBody("{\"name\": \"Jason\"}").setResponseCode(300))

//        val baseUrl = server.url("https://slack.com/api/chat.postMessage")
        val baseUrl = server.url("chat.postMessage")
        val slackToken = "xoxb-xxxx"

        val okHttpClient = OkHttpClient.Builder().callTimeout(Duration.ofMillis(1000))
            .build()
        val formBody: RequestBody = FormBody.Builder()
            .add("channel", "testing")
            .add("text", "jayMessage from raw okHttpClient")
            .build()
        val request = Request.Builder()
            .url("https://slack.com/api/chat.postMessage")
//            .url(baseUrl)
            .addHeader("Authorization", "Bearer $slackToken")
            .post(formBody)
            .build()


        val call = okHttpClient.newCall(request);
        val response = call.execute();
        println("response = ${response}")

        val recordedRequest = server.takeRequest()
        println("recordedRequest = ${recordedRequest}")
        println("recordedRequest = ${recordedRequest.body}")



    }
}