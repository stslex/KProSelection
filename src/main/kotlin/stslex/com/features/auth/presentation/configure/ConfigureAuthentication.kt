package stslex.com.features.auth.presentation.configure

import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import stslex.com.features.auth.presentation.presenter.AuthPresenter

fun Application.configureAuthenticationFeature() {
    val presenter by inject<AuthPresenter>()
    configureAuthenticationPlugin(presenter)
    routing {
        configureAuthenticationRouter(presenter)
    }
}
