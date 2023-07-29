package stslex.com.features.auth.domain.result

import stslex.com.features.auth.presentation.model.RoutingUserResponse

sealed interface AuthResult {

    data class Success(
        val data: RoutingUserResponse
    ) : AuthResult

    data object InvalidPassword : AuthResult

    data object UserIsNotExist : AuthResult
}