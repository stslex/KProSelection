package stslex.com.features.auth.presentation.presenter.model

import stslex.com.features.auth.domain.model.RegisterDomainResult
import stslex.com.features.auth.domain.model.UserAuthDomainModel
import stslex.com.features.auth.presentation.model.respond.AuthUserResponse
import stslex.com.features.auth.presentation.utils.token.model.UserTokenModel

fun RegisterDomainResult.toPresentation() = when (this) {
    is RegisterDomainResult.SaveUserError -> RegisterResult.Error.SaveUserError
    is RegisterDomainResult.UserIsExist -> RegisterResult.Error.UserIsExist
    is RegisterDomainResult.Success -> RegisterResult.Success
}

fun UserAuthDomainModel.toTokenModel(): UserTokenModel = UserTokenModel(
    uuid = uuid,
    username = username,
    password = password
)

fun UserAuthDomainModel.toPresentation(
    token: String
) = AuthUserResponse(
    username = username,
    nickname = nickname,
    token = token
)