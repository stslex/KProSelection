package stslex.com.datasources.user.datasource

import stslex.com.datasources.user.model.UserEntity
import stslex.com.features.auth.domain.model.UserAuthModel

interface UserDataSource {

    suspend fun isUserExist(username: String): Boolean

    suspend fun saveUser(user: UserAuthModel): UserEntity?

    suspend fun getUser(username: String): UserEntity?

    suspend fun getUserByUuid(uuid: String): UserEntity?

    suspend fun getAll(): List<UserEntity>

    suspend fun clearAll()

    suspend fun deleteUser(username: String)
}