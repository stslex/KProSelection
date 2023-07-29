package stslex.com.features.auth.presentation

import io.ktor.server.application.*
import stslex.com.features.auth.presentation.plugin.installAuthPlugin
import stslex.com.features.auth.presentation.routing.installAuthRouting

fun Application.configureAuthentication() {
    installAuthPlugin()
    installAuthRouting()
}
