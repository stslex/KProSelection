package stslex.com.features.auth.domain.model

import stslex.com.features.auth.data.repository.model.UserDataModel
import stslex.com.features.auth.presentation.model.RoutingUserResponse

object AuthUserMapper {

    fun UserDataModel.toResponse(
        token: String
    ): RoutingUserResponse = RoutingUserResponse(
        uuid = uuid,
        username = username,
        nickname = nickname,
        token = token
    )

    fun UserDataModel.toAuth(): UserAuthModel = UserAuthModel(
        username = username,
        password = password
    )

    fun List<UserDataModel>.toDomain(): List<UserDomainModel> = map {
        it.toDomain()
    }

    fun UserDataModel.toDomain(): UserDomainModel = UserDomainModel(username)
}