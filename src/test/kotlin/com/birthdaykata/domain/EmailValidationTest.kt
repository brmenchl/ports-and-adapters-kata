package com.birthdaykata.domain

import arrow.core.invalidNel
import arrow.core.valid
import com.birthdaykata.domain.models.Email
import com.birthdaykata.domain.models.ValidationError
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class EmailValidationTest : FreeSpec({
    "Email.parse should return Valid" - {
        val validEmail = "brad@localhost"

        "$validEmail should be valid" {
            val email = Email.parse(validEmail)

            email.map { it.value } shouldBe validEmail.valid()
        }
    }

    "Email.parse should return Invalid" - {
        "null should be invalid" {
            val result = Email.parse(null)
            result shouldBe ValidationError("Email cannot be empty").invalidNel()
        }

        val invalidEmail = "not an email"

        "$invalidEmail should be invalid" {
            val result = Email.parse(invalidEmail)
            result shouldBe ValidationError("$invalidEmail is not a valid email address").invalidNel()
        }
    }
})