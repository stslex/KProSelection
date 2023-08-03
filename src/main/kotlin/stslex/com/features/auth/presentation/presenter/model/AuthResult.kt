package stslex.com.features.auth.presentation.presenter.model

import stslex.com.features.auth.presentation.model.respond.AuthUserRespond

sealed interface AuthResult {

    data class Success(
        val data: AuthUserRespond
    ) : AuthResult

    data object InvalidPassword : AuthResult

    data object UserIsNotExist : AuthResult
}