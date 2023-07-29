package stslex.com.domain.interactor

import stslex.com.data.repository.model.UserAuthModel
import stslex.com.domain.model.AuthResult
import stslex.com.domain.model.RegisterResult

interface AuthInteractor {

    suspend fun register(user: UserAuthModel): RegisterResult

    suspend fun auth(user: UserAuthModel): AuthResult
}