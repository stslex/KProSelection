package stslex.com.datasources.user.datasource

import stslex.com.datasources.user.model.UserEntity
import stslex.com.datasources.user.model.UserUpdateEntity
import stslex.com.features.auth.presentation.model.request.UserAuthRequest

interface UserDataSource {

    suspend fun isUserExist(username: String): Boolean

    suspend fun saveUser(user: UserAuthRequest): UserEntity?

    suspend fun getUser(username: String): UserEntity?

    suspend fun getUserByUuid(uuid: String): UserEntity?

    suspend fun updateFields(
        uuid: String,
        update: UserUpdateEntity
    ): UserEntity

    suspend fun getAll(
        page: Int,
        pageSize: Int
    ): List<UserEntity>

    suspend fun clearAll()

    suspend fun deleteUser(username: String)
}