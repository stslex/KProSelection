package stslex.com.domain.model

import stslex.com.routing.model.RoutingUserResponse

sealed interface RegisterResult {

    data class Success(
        val data: RoutingUserResponse
    ) : RegisterResult

    data object InvalidPassword : RegisterResult

    data object UserIsExist : RegisterResult

    data object SaveUserError : RegisterResult
}

