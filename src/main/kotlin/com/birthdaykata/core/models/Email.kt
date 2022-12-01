package com.birthdaykata.core.models

import arrow.core.NonEmptyList
import arrow.core.Validated
import arrow.core.invalidNel
import arrow.core.valid

data class ValidationError(val message: String)

typealias ValidationErrors = NonEmptyList<ValidationError>

@JvmInline
value class Email private constructor(val value: String) {
    companion object {
        fun todo(value: String?): Email = Email(value!!)
        fun parse(value: String?): Validated<ValidationErrors, Email> =
            when {
                value == null -> ValidationError("Email cannot be empty").invalidNel()
                isValidEmail(value) -> Email(value).valid()
                else -> ValidationError("$value is not a valid email address").invalidNel()
            }

        private fun isValidEmail(value: String) = value.contains('@')
    }
}
