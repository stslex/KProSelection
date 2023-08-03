package stslex.com.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import stslex.com.datasources.user.datasource.UserDataSource
import stslex.com.datasources.user.datasource.UserDataSourceImpl
import stslex.com.features.auth.data.repository.UserRepository
import stslex.com.features.auth.data.repository.AuthRepositoryImpl
import stslex.com.features.auth.domain.interactor.AuthInteractor
import stslex.com.features.auth.domain.interactor.AuthInteractorImpl
import stslex.com.features.auth.presentation.presenter.AuthPresenter
import stslex.com.features.auth.presentation.presenter.AuthPresenterImpl
import stslex.com.features.auth.presentation.utils.PasswordChecker
import stslex.com.features.auth.presentation.utils.PasswordCheckerImpl
import stslex.com.features.user.domain.UserInteractor
import stslex.com.features.user.domain.UserInteractorImpl

val koinModule = module {
    singleOf(::UserDataSourceImpl) { bind<UserDataSource>() }
    singleOf(::AuthRepositoryImpl) { bind<UserRepository>() }
    singleOf(::AuthInteractorImpl) { bind<AuthInteractor>() }
    singleOf(::AuthPresenterImpl) { bind<AuthPresenter>() }
    singleOf(::PasswordCheckerImpl) { bind<PasswordChecker>() }
    singleOf(::UserInteractorImpl) { bind<UserInteractor>() }
}
