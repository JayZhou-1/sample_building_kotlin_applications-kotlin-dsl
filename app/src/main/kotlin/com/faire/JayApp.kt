package com.faire

import com.google.inject.AbstractModule
import com.google.inject.Guice
import com.google.inject.Provides
import faire.BackFill
import faire.NotifyAfter
import javax.inject.Inject
import javax.inject.Qualifier
import okhttp3.Interceptor
import okhttp3.OkHttpClient

@NotifyAfter("2022-01-01")
class JayApp : BackFill() {

    @Inject
    lateinit var okHttpClientTest: OkHttpClientTest

    fun jayAppEntryPoint() {
//        okHttpClientTest.callSlack();
        println("=========with interceptor==================")
        okHttpClientTest.callGoogleWithOkHttpClientInterceptor();
        println("=============without interceptor==============")
        okHttpClientTest.callGoogleWithoutOkHttpClientInterceptor()
    }

}

fun main() {
    val injector = Guice.createInjector(GuiceModule())
    val jayApp = injector.getInstance(JayApp::class.java)
    jayApp.jayAppEntryPoint()
}


class GuiceModule : AbstractModule() {
    @Provides
    @WithTransactionInterceptor
    fun okHttpClientWithInjector(): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(Interceptor { chain ->
            println("it has interceptor")
            chain.proceed(chain.request())
        }).build()
    }

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class WithTransactionInterceptor
}
