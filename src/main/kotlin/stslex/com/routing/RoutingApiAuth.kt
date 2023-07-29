package stslex.com.routing

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import stslex.com.routing.RoutingExt.API_HOST
import stslex.com.routing.model.TestUser

fun Routing.routingApiAuth() {
    authenticate {
        get("$API_HOST/testUser") {
            call.respond(TestUser("Alex"))
        }
    }
}