package stslex.com.features.auth.presentation.configure

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import stslex.com.features.auth.presentation.presenter.AuthPresenter
import stslex.com.model.*
import stslex.com.routing.RoutingExt

private const val AUTH_END_POINT = "passport"
private const val AUTH_PATH = "${RoutingExt.API_HOST}/${AUTH_END_POINT}"

fun Routing.configureAuthenticationRouter(
    presenter: AuthPresenter
) {
    authenticate(AuthConfigType.DEFAULT.configName) {
        post("$AUTH_PATH/registration") {
            presenter.register(call.receive())
                .onSuccess(call::respondCreated)
                .onError(call::respondError)
        }

        post("$AUTH_PATH/login") {
            presenter.auth(call.receive())
                .onSuccess(call::respondAccept)
                .onError(call::respondError)
        }
    }
}
