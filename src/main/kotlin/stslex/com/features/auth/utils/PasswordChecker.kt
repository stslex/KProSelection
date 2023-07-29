package stslex.com.features.auth.utils

interface PasswordChecker {

    fun isValid(password: String): Boolean
}