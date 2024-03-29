package stslex.com.plugins

import io.ktor.server.application.*
import io.ktor.server.routing.*
import stslex.com.features.user.presentation.routingUser
import stslex.com.routing.routineSwagger
import stslex.com.routing.routingApiAuth
import stslex.com.routing.routingApiUnAuth
import stslex.com.routing.routingSite

fun Application.configureRouting() {
    routing {
        routineSwagger()
        routingSite()
        routingApiUnAuth()
        routingApiAuth()
        routingUser()
    }
}
