package stslex.com.data.datasource

import stslex.com.data.datasource.model.UserEntity
import stslex.com.data.repository.model.UserAuthModel

interface UserDataSource {

    suspend fun isUserExist(username: String): Boolean

    suspend fun saveUser(user: UserAuthModel): UserEntity?

    suspend fun getUser(username: String): UserEntity?
}