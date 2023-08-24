package stslex.com

import com.typesafe.config.ConfigFactory
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.config.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.request.*
import stslex.com.datasources.user.table.DatabaseFactory.initDatabase
import stslex.com.plugins.*

fun main() {
    embeddedServer(
        factory = Netty,
        port = 8080,
        host = "0.0.0.0",
        module = Application::module
    )
        .start(
            wait = true
        )
}

suspend fun ApplicationCall.redirectInternally(path: String) {
    val cp = object : RequestConnectionPoint by this.request.local {
        override val uri: String = path
    }
    val req = object : ApplicationRequest by this.request {
        override val local: RequestConnectionPoint = cp
    }
    val call = object : ApplicationCall by this {
        override val request: ApplicationRequest = req
    }
    this.application.sendPipeline.execute(call, call.response as Any)
}

fun Application.module() {
    configureDI()
    initDatabase()
    configureSerialization()
    configureTemplating()
    configureAuthentication()
    configureRouting()
}

val config: HoconApplicationConfig = HoconApplicationConfig(ConfigFactory.load("application.conf"))
