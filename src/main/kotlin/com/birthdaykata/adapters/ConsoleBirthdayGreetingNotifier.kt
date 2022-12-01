package com.birthdaykata.adapters

import com.birthdaykata.core.ports.BirthdayGreetingNotifier
import com.birthdaykata.core.models.Employee

class ConsoleBirthdayGreetingNotifier : BirthdayGreetingNotifier {
    override fun sendBirthdayGreeting(employee: Employee) = println(birthdayGreeting(employee))

    private fun birthdayGreeting(employee: Employee): String =
        "Subject: Happy birthday!\nHappy birthday, dear ${employee.name.firstName}!"
}
