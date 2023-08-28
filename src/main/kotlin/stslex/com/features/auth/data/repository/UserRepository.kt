package stslex.com.features.auth.data.repository

import stslex.com.features.auth.data.repository.model.UserDataModel
import stslex.com.features.auth.presentation.model.request.UserAuthRequest

interface UserRepository {

    suspend fun isUserExist(username: String): Boolean

    suspend fun saveUser(user: UserAuthRequest): UserDataModel?

    suspend fun getUser(username: String): UserDataModel?

    suspend fun getUserByUuid(uuid: String): UserDataModel?

    suspend fun clearAll()
}