package com.paratera.utils

import io.vertx.core.json.JsonArray
import io.vertx.ext.asyncsql.AsyncSQLClient
import io.vertx.ext.sql.ResultSet
import io.vertx.ext.sql.SQLClient
import io.vertx.ext.sql.SQLConnection
import io.vertx.ext.sql.UpdateResult
import io.vertx.kotlin.coroutines.awaitResult

suspend fun AsyncSQLClient.getConnection() : SQLConnection {
    return awaitResult {
        this.getConnection(it)
    }
}

suspend fun AsyncSQLClient.query(sql: String) : ResultSet {
    return awaitResult {
        this.query(sql, it)
    }
}

suspend fun AsyncSQLClient.queryWithParams(sql: String, args: JsonArray) : ResultSet {
    return awaitResult {
        this.queryWithParams(sql, args, it)
    }
}

suspend fun AsyncSQLClient.update(sql: String) : UpdateResult {
    return awaitResult {
        this.update(sql, it)
    }
}

suspend fun AsyncSQLClient.updateWithParams(sql: String, args: JsonArray) : UpdateResult {
    return awaitResult {
        this.updateWithParams(sql, args, it)
    }
}

suspend fun SQLConnection.queryWithsParams(sql: String, args: JsonArray) : ResultSet {
    return awaitResult {
        this.queryWithParams(sql, args, it)
    }
}

suspend fun SQLConnection.query(sql: String) : ResultSet {
    return awaitResult {
        this.query(sql, it)
    }
}


suspend fun SQLConnection.updateWithParams(sql: String, args: JsonArray) : UpdateResult {
    return awaitResult {
        this.updateWithParams(sql, args, it)
    }
}

suspend fun SQLConnection.update(sql: String) : UpdateResult {
    return awaitResult {
        this.update(sql, it)
    }
}

suspend fun SQLConnection.batchWithParams(sql: String, args: List<JsonArray>) : List<Int> {
    return awaitResult {
        this.batchWithParams(sql, args, it)
    }
}

suspend fun SQLConnection.batch(sqls: List<String>) : List<Int> {
    return awaitResult {
        this.batch(sqls, it)
    }
}

suspend fun SQLConnection.beginTx() {
    awaitResult<Void> {
        this.setAutoCommit(false, it)
    }
}

suspend fun SQLConnection.commitTx() {
    awaitResult<Void> {
        this.commit(it)
    }
}

suspend fun SQLConnection.rollbackTx() {
    awaitResult<Void> {
        this.rollback(it)
    }
}

suspend fun SQLConnection.executeSQL(sql: String) {
    awaitResult<Void> {
        this.execute(sql, it)
    }
}