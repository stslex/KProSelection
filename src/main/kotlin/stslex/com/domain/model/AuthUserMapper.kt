package stslex.com.domain.model

import stslex.com.data.repository.model.UserModel
import stslex.com.routing.model.RoutingUserResponse

object AuthUserMapper {

    fun UserModel.toResponse(): RoutingUserResponse = RoutingUserResponse(
        uuid = uuid,
        username = username,
        nickname = nickname
    )
}