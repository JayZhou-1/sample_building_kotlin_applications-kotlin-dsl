package com.faire

import com.google.gson.Gson
import io.github.cdimascio.dotenv.Dotenv
import java.time.Duration
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NotificationCliEntry {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val dotenv = Dotenv.load();
            val slackToken = dotenv.get("CODE_CLEANUP_BOT_SLACK_TOKEN")
            val slackClient = getSlackServiceApi()
            val messages = arrayOf("first", "second", "third")
            messages.forEach {
                val message = PostMessageRequest(channel = "testing", text = it)
                val call = slackClient.postMessageJSON(
                    authorization = "Bearer $slackToken",
                    message
                )
                val response = call.execute()
                println("response = ${response}")
            }
            println("ends")
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