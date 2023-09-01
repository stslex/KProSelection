package stslex.com.features.user.presentation.presenter

import stslex.com.features.user.presentation.model.UserResponse
import stslex.com.model.ApiResponse

interface UserPresenter {

    suspend fun getUsers(
        page: Int?,
        pageSize: Int?
    ): ApiResponse<List<UserResponse>>
}

