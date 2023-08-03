package stslex.com.features.auth.presentation

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import stslex.com.features.auth.presentation.model.response.UserAuthResponse
import stslex.com.features.auth.presentation.plugin.AuthConfig
import stslex.com.features.auth.presentation.presenter.AuthPresenter
import stslex.com.features.auth.presentation.utils.token.JwtUnAuthConfig.API_KEY_HEADER
import stslex.com.features.auth.presentation.utils.token.JwtUnAuthConfig.DEVICE_ID_HEADER
import stslex.com.features.auth.presentation.utils.token.JwtUnAuthConfig.UUID_HEADER
import stslex.com.routing.RoutingExt

fun Routing.routingAuth() {
    val presenter by inject<AuthPresenter>()

    authenticate(
        AuthConfig.JWT_TOKEN.configName,
        AuthConfig.JWT_TOKEN_AUTH.configName
    ) {
        post("${RoutingExt.API_HOST}/passport/register") {
            val user = call.receive<UserAuthResponse>()
            val result = presenter.register(user)
            processRegister(result)
        }

        post("${RoutingExt.API_HOST}/passport/auth") {
            val user = call.receive<UserAuthResponse>()
            val result = presenter.auth(user)
            processAuth(result)
        }
    }

    get("${RoutingExt.API_HOST}/token") {
        val uuid = call.request.header(UUID_HEADER)
        val tokenModel = if (uuid != null) presenter.getUserTokenModel(uuid) else null
        processTokenGenerate(
            apiKey = call.request.header(API_KEY_HEADER),
            deviceId = call.request.header(DEVICE_ID_HEADER),
            tokenModel = tokenModel,
        )
    }
}
