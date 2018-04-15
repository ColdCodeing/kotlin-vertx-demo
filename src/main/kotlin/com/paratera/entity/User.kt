package com.paratera.entity

import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import io.vertx.kotlin.core.json.json
import io.vertx.kotlin.core.json.obj

data class User(var username: String = "", var password: String = "") : BaseEntity(){
    fun fromJson(obj: JsonObject) : User {
        this.username = obj.getString("username")
        this.password = obj.getString("password")
        return this
    }

    fun fromJson(array: JsonArray) :User {
        this.username = array.getString(0);
        this.password = array.getString(1);
        return this
    }

    override fun toJson() : String {
        val jsonObject = JsonObject()
        jsonObject.put("username", username)
        jsonObject.put("password", password)
        return jsonObject.toString()
    }
}