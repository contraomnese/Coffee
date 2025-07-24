package com.contraomnese.coffee.domain.auth.model

@JvmInline value class Email(val value: String) {
    fun isValidEmail() : Boolean {
        val emailRegex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()
        return value.matches(emailRegex) || value.isEmpty()
    }
}

@JvmInline value class Password(val value: String)

data class UserParams(
    val login: Email,
    val password: Password
)

data class Token(
    val token: String,
    val lifeTime: Long
)