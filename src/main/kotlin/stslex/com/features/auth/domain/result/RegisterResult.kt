package stslex.com.features.auth.domain.result

import stslex.com.features.auth.presentation.model.RoutingUserResponse

sealed interface RegisterResult {

    data class Success(
        val data: RoutingUserResponse
    ) : RegisterResult

    data object InvalidPassword : RegisterResult

    data object UserIsExist : RegisterResult

    data object SaveUserError : RegisterResult
}

