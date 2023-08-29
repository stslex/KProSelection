package stslex.com.features.auth.presentation.presenter

import stslex.com.features.auth.presentation.model.request.UserAuthRequest
import stslex.com.features.auth.presentation.presenter.model.AuthResult
import stslex.com.features.auth.presentation.presenter.model.RegisterResult
import stslex.com.features.auth.presentation.token.UserTokenModel

interface AuthPresenter {

    suspend fun register(user: UserAuthRequest): RegisterResult<Any>

    suspend fun auth(user: UserAuthRequest): AuthResult<Any>

    suspend fun getUserTokenModel(uuid: String): UserTokenModel?

    suspend fun isUserExist(uuid: String): Boolean
}