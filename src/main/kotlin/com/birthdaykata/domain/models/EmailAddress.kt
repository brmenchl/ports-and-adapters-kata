package com.birthdaykata.domain.models

@JvmInline
value class EmailAddress private constructor(val value: String) {
    companion object {
        fun fromString(str: String) = EmailAddress(str)
    }
}
