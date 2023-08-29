package stslex.com.features.auth.presentation.configure

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import stslex.com.features.auth.presentation.model.request.UserAuthRequest
import stslex.com.features.auth.presentation.presenter.AuthPresenter
import stslex.com.routing.RoutingExt

private const val AUTH_END_POINT = "passport"
private const val AUTH_PATH = "${RoutingExt.API_HOST}/${AUTH_END_POINT}"

fun Routing.configureAuthenticationRouter(
    presenter: AuthPresenter
) {
    authenticate(AuthConfigType.DEFAULT.configName) {
        post("$AUTH_PATH/registration") {
            val user = call.receive<UserAuthRequest>()
            val result = presenter.register(user)
            call.respond(result.statusCode, result.data)
        }

        post("$AUTH_PATH/login") {
            val user = call.receive<UserAuthRequest>()
            val result = presenter.auth(user)
            call.respond(result.statusCode, result.data)
        }
    }
}