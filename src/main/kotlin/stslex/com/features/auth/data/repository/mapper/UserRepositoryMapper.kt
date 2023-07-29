package stslex.com.features.auth.data.repository.mapper

import stslex.com.datasources.user.model.UserEntity
import stslex.com.features.auth.data.repository.model.UserDataModel

object UserRepositoryMapper {

    fun UserEntity.toDomain(): UserDataModel = UserDataModel(
        uuid = uuid,
        username = username,
        nickname = nickname,
        password = password
    )
}