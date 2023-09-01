package stslex.com.features.auth.presentation.presenter.model

import stslex.com.features.auth.domain.model.RegisterDomainResult
import stslex.com.features.auth.domain.model.UserAuthDomainModel
import stslex.com.features.auth.presentation.model.response.AuthUserResponse
import stslex.com.features.auth.presentation.token.UserTokenModel
import stslex.com.model.ApiError
import stslex.com.model.ApiResponse
import stslex.com.model.MessageResponse

fun RegisterDomainResult.toPresentation(): ApiResponse<MessageResponse> = when (this) {
    is RegisterDomainResult.SaveUserError -> ApiResponse.Error(ApiError.Authentication.SaveUser)
    is RegisterDomainResult.UserIsExist -> ApiResponse.Error(ApiError.Authentication.UserIsExist)
    is RegisterDomainResult.Success -> ApiResponse.Success(MessageResponse("success register"))
}

fun UserAuthDomainModel.toTokenModel(): UserTokenModel = UserTokenModel(
    uuid = uuid,
    username = username,
    password = password
)

fun UserAuthDomainModel.toPresentation(
    token: String
) = AuthUserResponse(
    uuid = uuid,
    username = username,
    nickname = nickname,
    token = token
)