package com.yuventius.sample_project

import Greeting
import SERVER_PORT
import com.yuventius.sample_project.config.DatabaseConfig
import com.yuventius.sample_project.service.UserService
import data.User
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.logging.*
import kotlinx.serialization.json.Json
import org.ktorm.logging.Logger

internal val logger = KtorSimpleLogger(Application::class.java.simpleName)

fun main() {
//    UserService.insertUser("A", User.Gender.MALE)
//    UserService.insertUser("B", User.Gender.FEMALE)
//    UserService.insertUser("C", User.Gender.MALE)

    embeddedServer(Netty, port = SERVER_PORT, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        })
    }
    routing {
        get("/dsl") {
            val nickname: String? = call.request.queryParameters["nickname"]
            logger.info("nickname is ${nickname}")
            call.respond(UserService.getUsersByDSL(nickname))
        }

        get("/sequence") {
            val nickname: String? = call.request.queryParameters["nickname"]
            call.respond(UserService.getUsersBySequence(nickname))
        }

        get("/nativeQuery") {
            val nickname: String? = call.request.queryParameters["nickname"]
            call.respond(UserService.getUserByNativeQuery(nickname))
        }
    }
}