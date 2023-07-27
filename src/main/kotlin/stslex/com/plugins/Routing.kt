package stslex.com.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.io.File

fun Application.configureRouting() {
    routing {
        get("") {
            val currentHtml = getIndexString()
            call.respondText(currentHtml, ContentType.Text.Html)
        }
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

private fun getIndexString(): String = try {
    File("site/index.html").readText()
} catch (exception: Exception) {
    ""
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
