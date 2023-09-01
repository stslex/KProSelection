package stslex.com.features.auth.presentation.presenter

import stslex.com.features.auth.domain.interactor.AuthInteractor
import stslex.com.features.auth.presentation.model.request.UserAuthRequest
import stslex.com.features.auth.presentation.model.response.AuthUserResponse
import stslex.com.features.auth.presentation.presenter.model.toPresentation
import stslex.com.features.auth.presentation.presenter.model.toTokenModel
import stslex.com.features.auth.presentation.token.TokenGenerator
import stslex.com.features.auth.presentation.token.UserTokenModel
import stslex.com.model.ApiError.Authentication.*
import stslex.com.model.ApiResponse
import stslex.com.model.MessageResponse

class AuthPresenterImpl(
    private val interactor: AuthInteractor,
    private val tokenGenerator: TokenGenerator
) : AuthPresenter {

    override suspend fun register(
        user: UserAuthRequest
    ): ApiResponse<MessageResponse> {
        if (user.username.isBlank()) {
            return ApiResponse.Error(InvalidUsername)
        }
        if (user.password.isBlank()) {
            return ApiResponse.Error(InvalidPassword)
        }
        return interactor.register(user).toPresentation()
    }

    override suspend fun auth(user: UserAuthRequest): ApiResponse<AuthUserResponse> {
        val userDomain = interactor.getUserByUsername(
            username = user.username
        ) ?: return ApiResponse.Error(UserIsNotExist)
        if (userDomain.password != user.password) {
            return ApiResponse.Error(InvalidPassword)
        }
        val tokenModel = userDomain.toTokenModel()
        val token = tokenGenerator.generateToken(tokenModel)
        return ApiResponse.Success(
            data = userDomain.toPresentation(token)
        )
    }

    override suspend fun getUserTokenModel(
        uuid: String
    ): UserTokenModel? = interactor.getUserByUuid(uuid)?.toTokenModel()

    override suspend fun isUserValid(
        uuid: String,
        username: String,
        password: String
    ): Boolean = interactor
        .getUserByUuid(uuid)
        ?.let { user ->
            user.username == username &&
                    user.password == password
        }
        ?: false
}