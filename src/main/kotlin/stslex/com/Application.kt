package stslex.com

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import stslex.com.datasources.user.table.DatabaseFactory
import stslex.com.plugins.*

fun main() {
    embeddedServer(
        factory = Netty,
        port = 8080,
        host = "0.0.0.0",
        module = Application::module
    )
        .start(wait = true)
}

fun Application.module() {
    configureDI()
    DatabaseFactory.init()
    configureSerialization()
    configureTemplating()
    configureAuthentication()
    configureRouting()
}
