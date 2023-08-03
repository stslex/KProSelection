package stslex.com.features.auth.presentation.presenter

import stslex.com.features.auth.presentation.model.response.UserAuthResponse
import stslex.com.features.auth.presentation.presenter.model.AuthResult
import stslex.com.features.auth.presentation.presenter.model.RegisterResult
import stslex.com.features.auth.presentation.utils.token.model.UserTokenModel

interface AuthPresenter {

    suspend fun register(user: UserAuthResponse): RegisterResult

    suspend fun auth(user: UserAuthResponse): AuthResult

    suspend fun getUserTokenModel(uuid: String): UserTokenModel?
}