package stslex.com.plugins

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.io.File

fun Application.configureRouting() {
    routing {
        staticFiles(
            remotePath = "",
            dir = File("site"),
            index = "index.html"
        )
        get("/api/v1/hello") {
            call.respond(HelloResponse("hello"))
        }
        get("/api/v1/hello/{username}") {
            val username = call.parameters["username"].orEmpty()
            val helloText = "hello $username"
            val response = HelloResponse(helloText)
            call.respond(response)
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
data class HelloResponse(
    @SerialName("text")
    val hello: String
)
