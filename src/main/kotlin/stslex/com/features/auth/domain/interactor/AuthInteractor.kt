package stslex.com.features.auth.domain.interactor

import stslex.com.features.auth.domain.model.UserAuthModel
import stslex.com.features.auth.domain.model.UserDomainModel
import stslex.com.features.auth.domain.result.AuthResult
import stslex.com.features.auth.domain.result.RegisterResult
import stslex.com.features.auth.utils.token.model.UserTokenModel

interface AuthInteractor {

    suspend fun register(user: UserAuthModel): RegisterResult

    suspend fun auth(user: UserAuthModel): AuthResult

    suspend fun getAll(): List<UserDomainModel>

    suspend fun getUserTokenModel(uuid: String): UserTokenModel?

    suspend fun clearAll()
}