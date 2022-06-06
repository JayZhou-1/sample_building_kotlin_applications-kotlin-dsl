package com.faire

import com.google.inject.AbstractModule
import com.google.inject.Guice
import com.google.inject.Provides
import com.google.inject.util.Modules
import javax.inject.Inject
import okhttp3.OkHttpClient


class MainClass {
    @field:Inject
    lateinit var okHttpClient: OkHttpClient

    fun main() {
        println("okHttpClient = ${okHttpClient}")
    }
}

fun main() {
    val injector = Guice.createInjector(
        Modules.requireAtInjectOnConstructorsModule(),
        GuiceModuleWithProvides(),
        object : AbstractModule() {
            override fun configure() {
//                bind(MainClass::class.java).toInstance(MainClass()) // works
            }
        },
    )
    injector.getInstance(MainClass::class.java).main()
}


class GuiceModuleWithProvides : AbstractModule() {

    @Provides
    // need to pass `okHttpClient`, it will not inject it ,
    // if use `.toInstance(MainClass())`, then don't need to manually inject
    fun getMainClass(okHttpClient: OkHttpClient): MainClass {
        val mainClass = MainClass()
        mainClass.okHttpClient = okHttpClient
        return mainClass
    }

    @Provides
    fun createOkHttpClient(): OkHttpClient {
        return OkHttpClient()
    }
}