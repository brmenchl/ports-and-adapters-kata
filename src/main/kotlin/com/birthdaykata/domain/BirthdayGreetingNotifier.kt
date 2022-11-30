package com.birthdaykata.domain

import com.birthdaykata.domain.models.Employee

interface BirthdayGreetingNotifier {
    fun sendBirthdayGreeting(employee: Employee)
}
