package com.birthdaykata.controllers

import com.birthdaykata.adapters.FileSystemEmployeeRepository
import com.birthdaykata.adapters.ConsoleBirthdayGreetingNotifier
import com.birthdaykata.adapters.OneOffBirthdayGreetingTrigger
import com.birthdaykata.usecases.BirthdayGreeter
import com.birthdaykata.utils.SystemClock
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

@Controller("/birthday-greetings")
class BirthdayGreetingController {
    @Get(produces = [MediaType.TEXT_PLAIN])
    fun index() {
        val employeeRepository = FileSystemEmployeeRepository("employees.csv")
        val consoleBirthdayGreetingNotifier = ConsoleBirthdayGreetingNotifier()
        val birthdayService = BirthdayGreeter(employeeRepository, consoleBirthdayGreetingNotifier)
        val trigger = OneOffBirthdayGreetingTrigger(SystemClock(), birthdayService)
        trigger.run()
    }
}