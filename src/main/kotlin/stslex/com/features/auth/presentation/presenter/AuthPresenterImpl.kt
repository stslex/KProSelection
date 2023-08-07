package stslex.com.features.auth.presentation.presenter

import stslex.com.features.auth.domain.interactor.AuthInteractor
import stslex.com.features.auth.presentation.model.response.UserAuthResponse
import stslex.com.features.auth.presentation.presenter.model.AuthResult
import stslex.com.features.auth.presentation.presenter.model.RegisterResult
import stslex.com.features.auth.presentation.presenter.model.toPresentation
import stslex.com.features.auth.presentation.presenter.model.toTokenModel
import stslex.com.features.auth.presentation.utils.PasswordChecker
import stslex.com.features.auth.presentation.utils.token.JwtConfig
import stslex.com.features.auth.presentation.utils.token.model.UserTokenModel

class AuthPresenterImpl(
    private val interactor: AuthInteractor,
    private val passwordChecker: PasswordChecker,
) : AuthPresenter {

    override suspend fun register(
        user: UserAuthResponse
    ): RegisterResult<Any> {
        if (passwordChecker.isValid(user.password).not()) {
            return RegisterResult.Error.InvalidPassword
        }
        val userDomain = interactor.register(user)
        val tokenModel = userDomain.toTokenModel()
        val token = tokenModel?.let(JwtConfig::generateToken).orEmpty()
        return userDomain.toPresentation(token)
    }

    override suspend fun auth(user: UserAuthResponse): AuthResult<Any> {
        val userDomain = interactor.getUserByUsername(
            username = user.username
        ) ?: return AuthResult.Error.UserIsNotExist
        if (userDomain.password != user.password) return AuthResult.Error.InvalidPassword
        val tokenModel = userDomain.toTokenModel()
        val token = JwtConfig.generateToken(tokenModel)
        return AuthResult.Success(userDomain.toPresentation(token))
    }

    override suspend fun getUserTokenModel(
        uuid: String
    ): UserTokenModel? = interactor.getUserByUuid(uuid)?.toTokenModel()
}