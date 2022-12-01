package com.birthdaykata.usecases

import arrow.core.Either
import com.birthdaykata.domain.BirthdayChecker
import com.birthdaykata.domain.BirthdayGreetingNotifier
import com.birthdaykata.domain.EmployeeRepository
import com.birthdaykata.domain.models.Error
import java.time.LocalDate

class BirthdayGreeter(
    private val employeeRepository: EmployeeRepository,
    private val birthdayGreetingNotifier: BirthdayGreetingNotifier
) {
    fun sendGreetings(date: LocalDate): Either<Error, Unit> =
        employeeRepository.getEmployees()
            .map { es ->
                es
                    .filter {
                        BirthdayChecker.isBirthday(it.birthDate, date)
                                || BirthdayChecker.isLeapYearBirthday(it.birthDate, date)
                    }
                    .forEach(birthdayGreetingNotifier::sendBirthdayGreeting)
            }
}
