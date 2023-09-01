package stslex.com.model

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*


suspend fun ApplicationCall.respondError(error: ApiError) {
    respond(error.statusCode, error.data)
}

suspend inline fun <reified T : Any> ApplicationCall.respondOK(data: T) {
    respond(HttpStatusCode.OK, data)
}

suspend inline fun <reified T : Any> ApplicationCall.respondAccept(data: T) {
    respond(HttpStatusCode.Accepted, data)
}

suspend inline fun <reified T : Any> ApplicationCall.respondCreated(data: T) {
    respond(HttpStatusCode.Created, data)
}