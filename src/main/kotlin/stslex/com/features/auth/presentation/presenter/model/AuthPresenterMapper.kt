package stslex.com.features.auth.presentation.presenter.model

import stslex.com.features.auth.domain.model.RegisterDomainResult
import stslex.com.features.auth.domain.model.UserAuthDomainModel
import stslex.com.features.auth.presentation.model.respond.AuthUserRespond
import stslex.com.features.auth.presentation.utils.token.model.UserTokenModel

fun RegisterDomainResult.toPresentation(
    token: String
) = when (this) {
    is RegisterDomainResult.SaveUserError -> RegisterResult.Error.SaveUserError
    is RegisterDomainResult.UserIsExist -> RegisterResult.Error.UserIsExist
    is RegisterDomainResult.Success -> RegisterResult.Success(
        data = data.toPresentation(token)
    )
}

fun RegisterDomainResult.toTokenModel(): UserTokenModel? = takeIfSuccess?.toTokenModel()

fun UserAuthDomainModel.toTokenModel(): UserTokenModel = UserTokenModel(
    uuid = uuid,
    username = username,
    password = password
)

fun UserAuthDomainModel.toPresentation(
    token: String
) = AuthUserRespond(
    uuid = uuid,
    username = username,
    nickname = nickname,
    token = token
)