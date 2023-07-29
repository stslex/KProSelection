package stslex.com.domain.model

import stslex.com.routing.model.RoutingUserResponse

sealed interface AuthResult {

    data class Success(
        val data: RoutingUserResponse
    ) : AuthResult

    data object InvalidPassword : AuthResult

    data object UserIsNotExist : AuthResult
}