package stslex.com.domain.utils

interface PasswordChecker {

    fun isValid(password: String): Boolean
}