package com.birthdaykata.domain.models

import arrow.core.Validated
import arrow.core.zip
import arrow.typeclasses.Semigroup

data class EmailRoute private constructor(
    val from: Email,
    val to: Email
) {
    companion object {
        fun create(from: Validated<ValidationErrors, Email>, to: Validated<ValidationErrors, Email>) =
            from.zip(Semigroup.nonEmptyList(), to) { validFrom, validTo ->
                EmailRoute(validFrom, validTo)
            }
    }
}