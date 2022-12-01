package com.birthdaykata.adapters

import com.birthdaykata.domain.models.Employee
import com.birthdaykata.domain.ports.BirthdayGreetingNotifier

class ConsoleBirthdayGreetingNotifier : BirthdayGreetingNotifier {
    override fun sendBirthdayGreeting(employee: Employee) = println(birthdayGreeting(employee))

    private fun birthdayGreeting(employee: Employee): String =
        """
            From: yourjob@example.com
            To: ${employee.email}
            Subject: Happy birthday!
            Body: Happy birthday, dear ${employee.name.firstName}!
        """
}
