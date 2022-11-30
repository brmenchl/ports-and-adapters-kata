package com.birthdaykata.domain.models

import java.time.LocalDate

data class Employee(val name: FullName, val birthDate: LocalDate, val email: EmailAddress)
