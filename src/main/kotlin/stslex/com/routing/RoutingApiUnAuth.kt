package stslex.com.routing

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import stslex.com.routing.RoutingExt.API_HOST
import stslex.com.routing.model.HelloResponse

fun Routing.routingApiUnAuth() {
    get("$API_HOST/hello") {
        call.respond(HelloResponse("hello"))
    }
    get("$API_HOST/test/v2") {
        call.respond(HelloResponse("hello"))
    }
    get("$API_HOST/hello/{username}") {
        val username = call.parameters["username"].orEmpty()
        val helloText = "hello $username"
        val response = HelloResponse(helloText)
        call.respond(response)
    }
}