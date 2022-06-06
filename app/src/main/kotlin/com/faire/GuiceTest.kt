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

@NotifyAfter("2022-01-01")
class GuiceTest : BackFill() {

    @Inject
    lateinit var okHttpClientTest: OkHttpClientTest

    @Inject
    @field:WithTransactionInterceptor
    lateinit var okHttpClient: OkHttpClient

    fun main() {
        println("okHttpClient = ${okHttpClient}")
        okHttpClientTest.callSlack();
        okHttpClientTest.callGoogleWithOkHttpClientInterceptor()
    }

}

fun main() {
    val injector = Guice.createInjector(
        Modules.requireAtInjectOnConstructorsModule(),
        GuiceModule(),
        object : AbstractModule() {
            override fun configure() {
                //https://github.com/google/guice/wiki/Injections#automatic-injection
                // toInstance will automatically performs field and method injections
                // but @Provides will not
                bind(GuiceTest::class.java).toInstance(GuiceTest())
                bind(OkHttpClientTest::class.java).toInstance(OkHttpClientTest())
            }
        },
    )

    val jayApp = injector.getInstance(GuiceTest::class.java)
    jayApp.main()
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
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class WithTransactionInterceptor
