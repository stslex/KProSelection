package stslex.com.plugins

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

fun Application.configureRouting() {
    routing {
        get("/api/v1/hello") {
            call.respond(HelloRequest("hello"))
        }
    }
}

@Serializable
data class HelloRequest(
    @SerialName("text")
    val hello: String
)
