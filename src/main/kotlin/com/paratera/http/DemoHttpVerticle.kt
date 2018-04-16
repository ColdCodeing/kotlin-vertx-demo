package com.paratera.http

import com.paratera.entity.User
import com.paratera.utils.*
import io.vertx.core.Vertx
import io.vertx.ext.asyncsql.AsyncSQLClient
import io.vertx.ext.asyncsql.PostgreSQLClient
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.kotlin.core.json.JsonObject
import io.vertx.kotlin.core.json.json
import io.vertx.kotlin.core.json.obj
import io.vertx.kotlin.coroutines.CoroutineVerticle
import javafx.beans.binding.Bindings.isNotEmpty

class DemoHttpVerticle : CoroutineVerticle() {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Vertx.vertx().deployVerticle(DemoHttpVerticle())
        }
    }
    lateinit var postgreSQLClient: AsyncSQLClient

    suspend override fun start() {
        var postgreSQLClientConfig = JsonObject()
        postgreSQLClientConfig.put("username", "mm")
        postgreSQLClientConfig.put("password", "111111")
        postgreSQLClientConfig.put("database", "datatest")
        postgreSQLClient = PostgreSQLClient.createShared(vertx, postgreSQLClientConfig)
        var router = Router.router(vertx)

        router.route("/").handler{ctx -> testHandler(ctx)}

        router.get("/users").coroutineHandler{ctx -> listUsers(ctx)}


        var httpServer = vertx.createHttpServer()
        httpServer.requestHandler(router::accept).listen(9999);
    }

    fun testHandler(routingContext: RoutingContext) {
        val t = routingContext.getParam("t", true);
        routingContext.response().end("hello")
        println(t)
    }

    suspend fun listUsers(routingContext: RoutingContext) {
        val username = routingContext.getParam("name", false)
        val password = routingContext.getParam("pass", false)
        var resultSet = postgreSQLClient.query("select \"username\", \"password\" from \"user\"")
        var users: List<User> = resultSet.getRows()?.map { User().fromJson(it) }!!.toList();
        routingContext.endWithJson(users)
    }
}
