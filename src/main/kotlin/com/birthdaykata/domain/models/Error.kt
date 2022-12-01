package com.birthdaykata.domain.models

sealed class Error(val msg: String) {
    data class Repository(private val value: String) : Error("Repository: $value")
}
