package stslex.com.plugins

import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import stslex.com.di.koinModule

fun Application.configureDI() {
    install(Koin) {
        slf4jLogger()
        modules(koinModule)
    }
}