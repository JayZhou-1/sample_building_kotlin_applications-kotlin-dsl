package com.faire

import com.google.inject.AbstractModule
import com.google.inject.Guice
import faire.BackFill
import faire.NeverNotify
import faire.NotifyAfter
import javax.inject.Inject
import okhttp3.OkHttpClient

@NeverNotify
class JayApp @Inject constructor(val builder: OkHttpClient.Builder): BackFill() {

    fun getHttpClient(): OkHttpClient {
        return builder.build()
    }

}

fun main() {
    val injector = Guice.createInjector(GuiceTestModule())
    val jayApp = injector.getInstance(JayApp::class.java)
    val client = jayApp.getHttpClient();
    println("client = ${client.callTimeoutMillis}")
}

@NeverNotify
class GuiceTestModule : AbstractModule() {

}

@NotifyAfter(date = "2022-07-04")
class JayApp2() {

}