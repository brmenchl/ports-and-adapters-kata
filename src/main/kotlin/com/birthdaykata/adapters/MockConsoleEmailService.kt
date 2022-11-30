package com.birthdaykata.adapters

import com.birthdaykata.domain.BirthdayGreetingNotifier
import com.birthdaykata.domain.models.Employee

class MockConsoleEmailService : BirthdayGreetingNotifier {
    override fun sendBirthdayGreeting(employee: Employee) = println(birthdayGreeting(employee))

    private fun birthdayGreeting(employee: Employee): String =
        "Subject: Happy birthday!\nHappy birthday, dear ${employee.name.firstName}!"
}
