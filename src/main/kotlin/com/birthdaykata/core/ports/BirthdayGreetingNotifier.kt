package com.birthdaykata.core.ports

import com.birthdaykata.core.models.Employee

interface BirthdayGreetingNotifier {
    fun sendBirthdayGreeting(employee: Employee)
}
