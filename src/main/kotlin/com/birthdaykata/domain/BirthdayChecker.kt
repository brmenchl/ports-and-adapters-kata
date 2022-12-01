package com.birthdaykata.domain

import java.time.LocalDate
import java.time.Month

object BirthdayChecker {
    fun isBirthday(birthDate: LocalDate, date: LocalDate) =
        birthDate.month == date.month && birthDate.dayOfMonth == date.dayOfMonth

    fun isLeapYearBirthday(birthDate: LocalDate, date: LocalDate) =
        birthDate.isLeapYear && birthDate.month == Month.FEBRUARY && birthDate.dayOfMonth == 29
                && !date.isLeapYear && date.month == Month.FEBRUARY && date.dayOfMonth == 28
}