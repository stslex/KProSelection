package stslex.com.features.auth.presentation.presenter.model

import stslex.com.features.auth.presentation.model.respond.AuthUserRespond

sealed interface RegisterResult {

    data class Success(
        val data: AuthUserRespond
    ) : RegisterResult

    data object InvalidPassword : RegisterResult

    data object UserIsExist : RegisterResult

    data object SaveUserError : RegisterResult
}

