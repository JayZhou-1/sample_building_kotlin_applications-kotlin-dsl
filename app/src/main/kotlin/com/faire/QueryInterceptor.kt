package com.faire

import com.google.inject.AbstractModule
import com.google.inject.Guice
import com.google.inject.multibindings.Multibinder
import com.google.inject.util.Modules
import javax.inject.Inject
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class QueryReplayerQueryInterceptor


interface QueryInterceptor {
    fun beforeQueryRun(sql: String)
}

//@Singleton
class QueryInterceptor1 @Inject constructor() : QueryInterceptor {
    override fun beforeQueryRun(sql: String) {
    }
}

//@Singleton
class QueryInterceptor2 @Inject constructor() : QueryInterceptor {
    override fun beforeQueryRun(sql: String) {
    }
}

//@Singleton
class QueryInterceptor3 @Inject constructor() : QueryInterceptor {
    override fun beforeQueryRun(sql: String) {
    }
}

class Consumer1 @Inject constructor(
    @QueryReplayerQueryInterceptor private val interceptors: Set<QueryInterceptor>,
    @field:QueryReplayerQueryInterceptor private val interceptors2: Set<QueryInterceptor>,
) {
    fun getInterceptorCount() {
        println("with @QueryReplayerQueryInterceptor interceptors.size: ${interceptors.size}")
        println("with @QueryReplayerQueryInterceptor interceptors2.size: ${interceptors2.size}")
    }
}

class Consumer2 @Inject constructor(
    private val interceptors: Set<QueryInterceptor>,
) {
    fun getInterceptorCount() {
        println("without @QueryReplayerQueryInterceptor interceptors.size: ${interceptors.size}")
    }
}

object QueryReplayerBinding : AbstractModule() {
    override fun configure() {
        val multibinder =
            Multibinder.newSetBinder(binder(), QueryInterceptor::class.java, QueryReplayerQueryInterceptor::class.java)
        multibinder.addBinding().to(QueryInterceptor1::class.java)
        multibinder.addBinding().to(QueryInterceptor2::class.java)
    }
}

object OriginalBinding : AbstractModule() {
    override fun configure() {
        val multibinder = Multibinder.newSetBinder(binder(), QueryInterceptor::class.java)
        multibinder.addBinding().to(QueryInterceptor1::class.java)
        multibinder.addBinding().to(QueryInterceptor2::class.java)
        multibinder.addBinding().to(QueryInterceptor3::class.java)
    }
}

fun main() {
    val injector = Guice.createInjector(
        Modules.requireAtInjectOnConstructorsModule(),
        QueryReplayerBinding,
        OriginalBinding,
    )

    val consumer1 = injector.getInstance(Consumer1::class.java)
    consumer1.getInterceptorCount()


    val consumer2 = injector.getInstance(Consumer2::class.java)
    consumer2.getInterceptorCount()
}
/**
1) [Guice/MissingConstructor]: No injectable constructor for type QueryInterceptor1..
add @Inject constructor()



 */

