package com.birthdaykata.domain

import java.time.LocalDate

class BirthdayGreeter(
    private val employeeRepository: EmployeeRepository,
    private val birthdayGreetingNotifier: BirthdayGreetingNotifier
) {
    fun sendGreetings(date: LocalDate) = employeeRepository.getEmployees()
        .filter { it.birthDate.month == date.month && it.birthDate.dayOfMonth == date.dayOfMonth }
        .forEach(birthdayGreetingNotifier::sendBirthdayGreeting)
}
