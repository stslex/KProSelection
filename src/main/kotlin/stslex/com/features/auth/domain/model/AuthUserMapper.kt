package stslex.com.features.auth.domain.model

import stslex.com.features.auth.data.repository.model.UserDataModel

object AuthUserMapper {

    fun UserDataModel.toDomain(): UserAuthDomainModel = UserAuthDomainModel(
        uuid = uuid,
        username = username,
        nickname = nickname,
        password = password
    )
}