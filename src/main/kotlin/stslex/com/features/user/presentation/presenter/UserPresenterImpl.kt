package stslex.com.features.user.presentation.presenter

import io.ktor.util.logging.*
import stslex.com.features.user.domain.UserInteractor
import stslex.com.features.user.presentation.model.UserResponse
import stslex.com.features.user.presentation.model.toResponse
import stslex.com.model.ApiError
import stslex.com.model.ApiResponse

class UserPresenterImpl(
    private val interactor: UserInteractor
) : UserPresenter {

    override suspend fun getUsers(
        page: Int?,
        pageSize: Int?
    ): ApiResponse<List<UserResponse>> = try {
        interactor
            .getAll(
                page = page ?: DEFAULT_PAGE,
                pageSize = pageSize ?: DEFAULT_PAGE_SIZE,
            )
            .let { items ->
                ApiResponse.Success(items.toResponse())
            }
    } catch (e: Exception) {
        KtorSimpleLogger("UserError").error(e)
        ApiResponse.Error(ApiError.Authentication.InvalidUsername)
    }

    companion object {

        private const val DEFAULT_PAGE = 0
        private const val DEFAULT_PAGE_SIZE = 10
    }
}