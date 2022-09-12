package com.faire

import com.google.inject.AbstractModule
import com.google.inject.Guice
import com.google.inject.Key
import com.google.inject.TypeLiteral
import com.google.inject.multibindings.Multibinder
import com.google.inject.util.Modules
import javax.inject.Inject
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class QueryReplayer


interface QueryInterceptor {
    fun beforeQueryRun(sql: String)
}

@Singleton
class QueryInterceptor1 @Inject constructor() : QueryInterceptor {
    override fun beforeQueryRun(sql: String) {
    }
}

class QueryInterceptor2 @Inject constructor() : QueryInterceptor {
    override fun beforeQueryRun(sql: String) {
    }
}

class QueryInterceptor3 @Inject constructor() : QueryInterceptor {
    override fun beforeQueryRun(sql: String) {
    }
}

class Consumer1 @Inject constructor(
    @QueryReplayer private val interceptors: Set<QueryInterceptor>,
    @param:QueryReplayer private val interceptors2: Set<QueryInterceptor>,
    @field:QueryReplayer private val interceptors3: Set<QueryInterceptor>,

//    @file:QueryReplayer private val interceptors5: Set<QueryInterceptor>,
    @get:QueryReplayer private val interceptors6: Set<QueryInterceptor>,
//    @set:QueryReplayer private val interceptors7: Set<QueryInterceptor>,
//    @receiver:QueryReplayer private val interceptors8: Set<QueryInterceptor>,
//    @setparam:QueryReplayer private val interceptors9: Set<QueryInterceptor>,
//    @delegate:QueryReplayer private val interceptors10: Set<QueryInterceptor>,
) {
    fun getInterceptorCount() {

        println("with @QueryReplayer interceptors.size: ${interceptors.size}")//2
        println("this is correct, kotlin automatically apply to param I guess \n")

        println("with @param:QueryReplayer interceptors2.size: ${interceptors2.size}")//2
        println("this is correct, because parameter pass value to field, so should use parameter\n ")

        println("with @field:QueryReplayer interceptors3.size: ${interceptors3.size}")//3
        println("this is incorrect, because parameter pass value to field, so if param not set properly, then field is not\n")

        println("with @get:QueryReplayer interceptors6.size: ${interceptors6.size}")//3
        println("An accessor will not be generated for 'interceptors6', so the annotation will not be written to the class file\n")

        println("there are 9 site targers: https://kotlinlang.org/docs/annotations.html#annotation-use-site-targets")
        println("1. file 2. property 3. field 4. get 5. set 6. receiver 7. param 8. setparam 9. delegate")

    }
}

class Consumer2 @Inject constructor(
    private val interceptors: Set<QueryInterceptor>,
) {
    fun getInterceptorCount() {
        // without @QueryReplayer interceptors.size: 3
        println("without @QueryReplayer interceptors.size: ${interceptors.size}")
    }
}

object QueryReplayerBinding : AbstractModule() {
    override fun configure() {
        val multibinder =
            Multibinder.newSetBinder(binder(), QueryInterceptor::class.java, QueryReplayer::class.java)
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
    println("this is to learn how to use multibinder to bindi interface and bind interface with annotation")

    // verify Multibinder.newSetBinder(binder(), QueryInterceptor::class.java, QueryReplayer::class.java)
    // can bind class with annotation
    val consumer1 = injector.getInstance(Consumer1::class.java)
    consumer1.getInterceptorCount()
    val consumer2 = injector.getInstance(Consumer2::class.java)
    consumer2.getInterceptorCount()


    val typeLiteral = object : TypeLiteral<Set<QueryInterceptor>>() {}

    val keyWithAnnotation = Key.get(typeLiteral, QueryReplayer::class.java)
    val keyWithoutAnnotation = Key.get(typeLiteral)

    val queryInterceptorWithAnnotation = injector.getInstance(keyWithAnnotation)
    val queryInterceptorWithoutAnnotation = injector.getInstance(keyWithoutAnnotation)

    assert(queryInterceptorWithAnnotation.size == 2) {
        "QueryReplayerBinding only has two binding for QueryInterceptor"
    }
    assert(queryInterceptorWithoutAnnotation.size == 3) {
        "OriginalBinding only has three binding for QueryInterceptor"
    }

    println("\nsingleton still works when have two bindings for the same class")
    assert(queryInterceptorWithAnnotation.intersect(queryInterceptorWithoutAnnotation).size == 1) {
        "QueryInterceptor1 is singleton, so the two set should have one common element"
    }


}
/**
1) [Guice/MissingConstructor]: No injectable constructor for type QueryInterceptor1..
add @Inject constructor()



 */

