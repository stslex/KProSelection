package stslex.com.features.auth.presentation

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import stslex.com.features.auth.presentation.model.request.UserAuthRequest
import stslex.com.features.auth.presentation.plugin.AuthConfig
import stslex.com.features.auth.presentation.presenter.AuthPresenter
import stslex.com.routing.RoutingExt

fun Routing.routingAuth() {
    val presenter by inject<AuthPresenter>()

    authenticate(AuthConfig.DEFAULT.configName) {
        post("${RoutingExt.API_HOST}/passport/register") {
            val user = call.receive<UserAuthRequest>()
            val result = presenter.register(user)
            call.respond(result.statusCode, result.data)
        }

        post("${RoutingExt.API_HOST}/passport/auth") {
            val user = call.receive<UserAuthRequest>()
            val result = presenter.auth(user)
            call.respond(result.statusCode, result.data)
        }
    }
}
