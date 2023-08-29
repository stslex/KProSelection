package stslex.com

import com.typesafe.config.ConfigFactory
import io.ktor.server.application.*
import io.ktor.server.config.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import stslex.com.datasources.user.table.DatabaseFactory.initDatabase
import stslex.com.features.auth.presentation.configure.configureAuthenticationFeature
import stslex.com.plugins.configureDI
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
    configureDI()
    initDatabase()
    configureSerialization()
    configureTemplating()
    configureAuthenticationFeature()
    configureRouting()
}

val config: HoconApplicationConfig = HoconApplicationConfig(ConfigFactory.load("application.conf"))
