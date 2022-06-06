package com.faire

import com.google.inject.AbstractModule
import com.google.inject.Guice
import com.google.inject.Provides
import com.google.inject.util.Modules
import faire.BackFill
import faire.NotifyAfter
import javax.inject.Inject
import javax.inject.Qualifier
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request

@NotifyAfter("2022-01-01")
class GuiceTest : BackFill() {

    @field:Inject
    lateinit var okHttpClientTest: OkHttpClientTest

    @field:Inject
    lateinit var okHttpClient: OkHttpClient

    fun main() {
        println("okHttpClient = ${okHttpClient}")
//        okHttpClientTest.callSlack();
        println("=========with interceptor==================")
//        okHttpClientTest.callGoogleWithOkHttpClientInterceptor();
        println("=============without interceptor==============")
        okHttpClientTest.callGoogleWithoutOkHttpClientInterceptor()
    }

}

fun main() {
    val injector = Guice.createInjector(
        Modules.requireAtInjectOnConstructorsModule(),
        GuiceModule(),
        object : AbstractModule() {
            override fun configure() {
//                bind(GuiceTest::class.java).toInstance(GuiceTest())
//                bind(OkHttpClientTest::class.java).toInstance(OkHttpClientTest())
            }
        },

        )

    val jayApp = injector.getInstance(GuiceTest::class.java)
    jayApp.main()
}


class GuiceModule : AbstractModule() {

    @Provides
    fun guiceTest(): GuiceTest {
        return GuiceTest()
    }

    @Provides
    fun createOkHttpClientTest(): OkHttpClientTest {
        return OkHttpClientTest()
    }

    @Provides
    fun okHttpClientWithoutInjector(): OkHttpClient {
        return OkHttpClient()
    }

    @Provides
    @WithTransactionInterceptor
    fun okHttpClientWithInjector(): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(Interceptor { chain ->
            println("it has interceptor")
            chain.proceed(chain.request())
        }).build()
    }


}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class WithTransactionInterceptor
