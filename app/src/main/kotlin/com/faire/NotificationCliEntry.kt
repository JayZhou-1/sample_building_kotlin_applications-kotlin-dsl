package com.faire

import com.google.gson.Gson
import io.github.cdimascio.dotenv.Dotenv
import java.time.Duration
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.concurrent.thread


class NotificationCliEntry {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            var slackToken = System.getenv("CODE_CLEANUP_BOT_SLACK_TOKEN")
            requestWithRawOkhttpClient(slackToken)
            requestWithSlackServiceApi(slackToken)

            val threadSet: Set<Thread> = Thread.getAllStackTraces().keys
            println(threadSet.size)
            threadSet.forEach {
                println("thread: ${it.name}, isDaemon? \t ${it.isDaemon}" )
            }
            System.exit(0)
        }


        private fun requestWithRawOkhttpClient(slackToken: String?) {
            val okHttpClient = OkHttpClient.Builder().build()
            val formBody: RequestBody = FormBody.Builder()
                .add("channel", "#testing")
                .add("text", "jayMessage from raw okHttpClient")
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

        private fun requestWithSlackServiceApi(slackToken: String?) {
            val slackClient = getSlackServiceApi()
            val messages = arrayOf("first", "second", "third")
            messages.forEach {
                val message = PostMessageRequest(channel = "#testing", text = it)
                val call = slackClient.postMessageJSON(
                    authorization = "Bearer $slackToken",
                    message
                )
                val response = call.execute()
                println("response = ${response}")
            }
        }

        private fun getSlackServiceApi(): SlackServiceApi {
            val gson = Gson()
            val client = OkHttpClient.Builder().callTimeout(Duration.ofMillis(1000)).build()

            val retrofit = Retrofit.Builder()
                .client(client)
                .baseUrl("https://slack.com/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
            val slackClient = retrofit.create(SlackServiceApi::class.java)
            return slackClient
        }
    }
}