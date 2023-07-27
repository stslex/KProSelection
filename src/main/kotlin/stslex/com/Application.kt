package stslex.com

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import stslex.com.plugins.configureAuthentication
import stslex.com.plugins.configureRouting
import stslex.com.plugins.configureSerialization
import stslex.com.plugins.configureTemplating

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
    configureSerialization()
    configureAuthentication()
    configureTemplating()
    configureRouting()
}
