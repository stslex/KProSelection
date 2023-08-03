package stslex.com.features.auth.presentation.utils.token.model

import stslex.com.features.auth.data.repository.model.UserDataModel

object UserTokenMapper {

    fun UserDataModel.toToken(): UserTokenModel = UserTokenModel(
        uuid = uuid,
        username = username,
        password = password
    )
}