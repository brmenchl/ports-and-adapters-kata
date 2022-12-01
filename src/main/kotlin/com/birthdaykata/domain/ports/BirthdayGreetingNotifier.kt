package com.birthdaykata.domain.ports

import com.birthdaykata.domain.models.Employee

interface BirthdayGreetingNotifier {
    fun sendBirthdayGreeting(employee: Employee)
}
