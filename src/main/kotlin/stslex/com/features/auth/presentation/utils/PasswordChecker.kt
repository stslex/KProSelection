package stslex.com.features.auth.presentation.utils

interface PasswordChecker {

    fun isValid(password: String): Boolean
}