package stslex.com.domain.utils

class PasswordCheckerImpl : PasswordChecker {

    override fun isValid(password: String): Boolean {
        if (password.length < 8) return false
        val digits = password
            .filter { char ->
                char.isDigit()
            }
        return (digits.isBlank() || digits.length == password.length).not()
    }
}