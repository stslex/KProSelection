package stslex.com.features.auth.presentation.presenter

import stslex.com.features.auth.presentation.model.request.UserAuthRequest
import stslex.com.features.auth.presentation.model.response.AuthUserResponse
import stslex.com.features.auth.presentation.token.UserTokenModel
import stslex.com.model.ApiResponse
import stslex.com.model.MessageResponse

interface AuthPresenter {

    suspend fun register(user: UserAuthRequest): ApiResponse<MessageResponse>

    suspend fun auth(user: UserAuthRequest): ApiResponse<AuthUserResponse>

    suspend fun getUserTokenModel(uuid: String): UserTokenModel?

    suspend fun isUserValid(
        uuid: String,
        username: String,
        password: String
    ): Boolean
}