package stslex.com.routing

import io.ktor.server.plugins.swagger.*
import io.ktor.server.routing.*

fun Routing.routineSwagger() {
    swaggerUI(
        path = "swagger",
        swaggerFile = "documentation/documentation.yaml"
    )
}