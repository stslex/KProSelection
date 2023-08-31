package stslex.com.features.user.presentation

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.*
import org.koin.ktor.ext.inject
import stslex.com.features.auth.presentation.configure.AuthConfigType
import stslex.com.features.user.domain.UserInteractor
import stslex.com.features.user.presentation.model.UserUpdateRequest
import stslex.com.features.user.presentation.model.toResponse
import stslex.com.routing.RoutingExt
import stslex.com.utils.payload_uuid

private const val USER_END_POINT = "user"
private const val USER_PATH = "${RoutingExt.API_HOST}/$USER_END_POINT"

fun Routing.routingUser() {
    val interactor by inject<UserInteractor>()

    authenticate(
        AuthConfigType.JWT_TOKEN_AUTH.configName,
        AuthConfigType.DEFAULT.configName
    ) {

        get("$USER_PATH/list") {
            val items = interactor.getAll(
                page = call.attributes.getOrNull(AttributeKey("page_number")) ?: 0,
                pageSize = call.attributes.getOrNull(AttributeKey("page_size")) ?: 10
            ).toResponse()
            call.respond(items)
        }

        get("$USER_PATH/{uuid}") {
            val user = payload_uuid
                ?.let { interactor.getUser(it) }
                ?.toResponse()
            if (user == null) {
                call.respond(HttpStatusCode.NotFound)
            } else {
                call.respond(user)
            }
        }

        put("$USER_PATH/update") {
            val request = call.receive<UserUpdateRequest>()
            val uuid = payload_uuid
            if (uuid == null) {
                call.respond(HttpStatusCode.NotFound)
                return@put
            }
            try {
                val response = interactor
                    .updateFields(
                        uuid = uuid,
                        update = request
                    )
                    .toResponse()
                call.respond(response)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError)
            }
        }
    }
}
