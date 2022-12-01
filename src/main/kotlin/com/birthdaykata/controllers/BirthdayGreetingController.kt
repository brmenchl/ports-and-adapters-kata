package com.birthdaykata.controllers

import com.birthdaykata.adapters.EmployeeFileSystemRepository
import com.birthdaykata.adapters.MockConsoleEmailService
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
        val employeeRepository = EmployeeFileSystemRepository("employees.csv")
        val mockConsoleEmailService = MockConsoleEmailService()
        val birthdayService = BirthdayGreeter(employeeRepository, mockConsoleEmailService)
        val trigger = OneOffBirthdayGreetingTrigger(SystemClock(), birthdayService)
        trigger.run()
    }
}