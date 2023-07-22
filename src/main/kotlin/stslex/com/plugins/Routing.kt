package stslex.com.plugins

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

fun Application.configureRouting() {
    routing {
        get("/api/v1/hello") {
            call.respond(HelloRequest("hello"))
        }
        get("/api/v1/hello-naum") {
            call.respond(HelloRequest("hello Naum!!"))
        }
        authenticate {
            get("/api/v1/testUser") {
                call.respond(TestUser("Alex"))
            }
        }
    }
}

@Serializable
data class TestUser(
    @SerialName("username")
    val username: String
)

@Serializable
data class HelloRequest(
    @SerialName("text")
    val hello: String
)
