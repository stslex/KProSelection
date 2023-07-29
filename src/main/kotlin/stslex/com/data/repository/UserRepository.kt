package stslex.com.data.repository

import stslex.com.data.repository.model.UserAuthModel
import stslex.com.data.repository.model.UserModel

interface UserRepository {

    suspend fun isUserExist(username: String): Boolean

    suspend fun saveUser(user: UserAuthModel): UserModel?

    suspend fun getUser(username: String): UserModel?
}