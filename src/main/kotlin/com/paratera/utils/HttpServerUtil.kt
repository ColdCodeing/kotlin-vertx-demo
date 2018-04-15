package com.paratera.utils

import io.vertx.core.http.HttpServerRequest
import io.vertx.core.http.HttpServerResponse
import io.vertx.ext.web.Route
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.kotlin.coroutines.dispatcher
import kotlinx.coroutines.experimental.launch


fun RoutingContext.getParam(key: String, required: Boolean) : String? {
    val request = this.request()
    var param = request.getParam(key)
    if (required) {
        param ?: throw NullPointerException(String.format("param [%s] can not be null", key))
    }
    return param
}

fun RoutingContext.getFormParam(key: String, required: Boolean) : String? {
    val request = this.request()
    var param = request.getFormAttribute(key)
    if (required) {
        param?: throw NullPointerException(String.format("param [%s] can not be null", key))
    }
    return param
}

fun RoutingContext.getHeader(key: String, required: Boolean) : String? {
    val request = this.request()
    var header = request.getHeader(key)
    if (required) {
        header?: throw NullPointerException(String.format("header [%s] can not be null", key))
    }
    return header
}

fun RoutingContext.end(string: String) {
    this.response().end(string)
}

fun RoutingContext.endWithJson(jsonString: String) {
    val response = this.response()
    response.putHeader("content-type", "application/json")
    response.end(jsonString)
}

fun RoutingContext.endWithHtml(textString: String) {
    val response = this.response()
    response.putHeader("content-type", "text/html")
    response.end(textString)
}

fun Route.handler(handler: (RoutingContext) -> Unit, failureHandler: (RoutingContext) -> Unit) {
    handler {
        try {
            handler(it)
        } catch(e: Exception) {
            it.fail(e)
        }
        failureHandler(it)
    }
}

fun Route.handler(handler: (RoutingContext) -> Unit) {
    handler {
        try {
            handler(it)
        } catch(e: Exception) {
            it.response().setStatusCode(500)
            it.response().end(e?.message)
        }
    }
}

fun Route.coroutineHandler(handler: suspend (RoutingContext) -> Unit, failureHandler: (RoutingContext) -> Unit) {
    handler { ctx ->
        launch(ctx.vertx().dispatcher()) {
            try {
                handler(ctx)
            } catch(e: Exception) {
                ctx.fail(e)
            }
            failureHandler(ctx)
        }
    }
}

fun Route.coroutineHandler(handler: suspend (RoutingContext) -> Unit) {
    handler { ctx ->
        launch(ctx.vertx().dispatcher()) {
            try {
                handler(ctx)
            } catch(e: Exception) {
                ctx.response().setStatusCode(500)
                ctx.response().end(e?.message)
            }
        }
    }
}
