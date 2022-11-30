package com.birthdaykata.domain

import java.time.LocalDate
import java.time.Month

class BirthdayGreeter(
    private val employeeRepository: EmployeeRepository,
    private val birthdayGreetingNotifier: BirthdayGreetingNotifier
) {
    fun sendGreetings(date: LocalDate) = employeeRepository.getEmployees()
        .filter { isBirthday(it.birthDate, date) || isLeapYearBirthday(it.birthDate, date) }
        .forEach(birthdayGreetingNotifier::sendBirthdayGreeting)

    private fun isBirthday(birthDate: LocalDate, date: LocalDate) =
        birthDate.month == date.month && birthDate.dayOfMonth == date.dayOfMonth

    private fun isLeapYearBirthday(birthDate: LocalDate, date: LocalDate) =
        birthDate.isLeapYear && birthDate.month == Month.FEBRUARY && birthDate.dayOfMonth == 29
                && !date.isLeapYear && date.month == Month.FEBRUARY && date.dayOfMonth == 28
}
