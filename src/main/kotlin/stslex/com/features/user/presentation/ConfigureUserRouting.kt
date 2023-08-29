package stslex.com.features.user.presentation

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.*
import org.koin.ktor.ext.inject
import stslex.com.features.auth.presentation.configure.AuthConfigType
import stslex.com.features.user.domain.UserInteractor
import stslex.com.features.user.presentation.model.UserUpdateResponse
import stslex.com.features.user.presentation.model.toRespond
import stslex.com.routing.RoutingExt

private const val USER_END_POINT = "user"
private const val USER_PATH = "${RoutingExt.API_HOST}/$USER_END_POINT"

fun Routing.routingUser() {
    val interactor by inject<UserInteractor>()

    authenticate(AuthConfigType.JWT_TOKEN_AUTH.configName) {

        get("$USER_PATH/list") {
            val items = interactor.getAll(
                page = call.attributes.getOrNull(AttributeKey("page_number")) ?: 0,
                pageSize = call.attributes.getOrNull(AttributeKey("page_size")) ?: 10
            ).toRespond()
            call.respond(items)
        }

        get("$USER_PATH/{uuid}") {
            val uuid = call.parameters["uuid"]
            val user = uuid?.let { interactor.getUser(it) }?.toRespond()
            if (user == null) {
                call.respond(HttpStatusCode.NotFound)
            } else {
                call.respond(user)
            }
        }

        put("$USER_PATH/update") {
            val request = call.receive<UserUpdateResponse>()
            val uuid = call.authentication.principal<JWTPrincipal>()?.jwtId
            if (uuid == null) {
                call.respond(HttpStatusCode.Unauthorized, "uuid should be empty")
                return@put
            }
            try {
                val response = interactor
                    .updateFields(
                        uuid = uuid,
                        update = request
                    )
                    .toRespond()
                call.respond(response)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError)
            }
        }
    }
}
