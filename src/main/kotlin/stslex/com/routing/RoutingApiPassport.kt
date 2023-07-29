package stslex.com.routing

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import stslex.com.data.repository.model.UserAuthModel
import stslex.com.domain.interactor.AuthInteractor
import stslex.com.domain.model.AuthResult
import stslex.com.domain.model.RegisterResult
import stslex.com.routing.RoutingExt.API_HOST
import stslex.com.routing.model.RoutingUserResponse

fun Routing.routingApiPassport() {
    val interactor by inject<AuthInteractor>()

    post("$API_HOST/passport/register") {
        call.request.receiveChannel()
        val user = call.receive<UserAuthModel>()

        when (val result = interactor.register(user)) {

            is RegisterResult.Success -> {
                call.respond(HttpStatusCode.Created, result.data)
            }

            is RegisterResult.SaveUserError -> {
                call.respond(HttpStatusCode.InternalServerError)
            }

            is RegisterResult.InvalidPassword -> {
                call.respond(HttpStatusCode.LengthRequired, RoutingUserResponse.DEFAULT)
            }

            is RegisterResult.UserIsExist -> {
                call.respond(HttpStatusCode.Conflict, RoutingUserResponse.DEFAULT)
            }
        }
    }

    post("$API_HOST/passport/auth") {
        call.request.receiveChannel()
        val user = call.receive<UserAuthModel>()

        when (val result = interactor.auth(user)) {

            is AuthResult.Success -> {
                call.respond(HttpStatusCode.Accepted, result.data)
            }

            is AuthResult.InvalidPassword -> {
                call.respond(HttpStatusCode.PreconditionFailed, RoutingUserResponse.DEFAULT)
            }

            is AuthResult.UserIsNotExist -> {
                call.respond(HttpStatusCode.NotAcceptable, RoutingUserResponse.DEFAULT)
            }
        }
    }
}


