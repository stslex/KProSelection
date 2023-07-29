package stslex.com.features.auth.domain.interactor

import stslex.com.features.auth.domain.model.UserAuthModel
import stslex.com.features.auth.domain.model.UserDomainModel
import stslex.com.features.auth.domain.result.AuthResult
import stslex.com.features.auth.domain.result.RegisterResult

interface AuthInteractor {

    suspend fun register(user: UserAuthModel): RegisterResult

    suspend fun auth(user: UserAuthModel): AuthResult

    suspend fun getAll(): List<UserDomainModel>
}