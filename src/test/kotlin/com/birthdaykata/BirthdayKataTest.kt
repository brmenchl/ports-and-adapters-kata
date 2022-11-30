package com.birthdaykata

import com.birthdaykata.adapters.OneOffBirthdayGreetingTrigger
import com.birthdaykata.domain.BirthdayGreeter
import com.birthdaykata.domain.BirthdayGreetingNotifier
import com.birthdaykata.domain.EmployeeRepository
import com.birthdaykata.domain.models.EmailAddress
import com.birthdaykata.domain.models.Employee
import com.birthdaykata.domain.models.FullName
import com.birthdaykata.utils.FixedClock
import io.kotest.core.spec.style.FunSpec
import io.mockk.*
import java.time.LocalDate

class BirthdayKataTest : FunSpec({
    val john = Employee(
        FullName("John", "Doe"),
        LocalDate.of(1991, 12, 25),
        EmailAddress.fromString("john@example.com")
    )
    val abby =
        Employee(FullName("Abby", "Blargh"), LocalDate.of(1992, 9, 20), EmailAddress.fromString("amy@google.com"))
    val caroline =
        Employee(FullName("Caroline", "Hmm"), LocalDate.of(1989, 7, 4), EmailAddress.fromString("caroline@google.com"))

    test("should not notify when list is empty") {
        // Secondary Adapters
        val employeeRepository = mockk<EmployeeRepository>() // stub
        every { employeeRepository.getEmployees() } returns emptyList()
        val birthdayGreetingNotifier = mockk<BirthdayGreetingNotifier>() // mock

        // Domain
        val birthdayGreeter = BirthdayGreeter(employeeRepository, birthdayGreetingNotifier)

        birthdayGreeter.sendGreetings(LocalDate.of(2022, 11, 29))

        verify(exactly = 0) { birthdayGreetingNotifier.sendBirthdayGreeting(any()) }
    }

    test("should not notify when list has one employee without a birthday on specified day") {
        // Secondary Adapters
        val employeeRepository = mockk<EmployeeRepository>() // stub
        every { employeeRepository.getEmployees() } returns listOf(john)

        val birthdayGreetingNotifier = mockk<BirthdayGreetingNotifier>() // mock

        // Domain
        val birthdayGreeter = BirthdayGreeter(employeeRepository, birthdayGreetingNotifier)

        birthdayGreeter.sendGreetings(LocalDate.of(2022, 3, 14))

        verify(exactly = 0) { birthdayGreetingNotifier.sendBirthdayGreeting(any()) }
    }

    test("should notify employee when list has one employee with a birthday on specified day") {
        // Secondary Adapters
        val employeeRepository = mockk<EmployeeRepository>() // stub
        every { employeeRepository.getEmployees() } returns listOf(john)

        val birthdayGreetingNotifier = mockk<BirthdayGreetingNotifier>() // mock
        every { birthdayGreetingNotifier.sendBirthdayGreeting(any()) } just Runs

        // Domain
        val birthdayGreeter = BirthdayGreeter(employeeRepository, birthdayGreetingNotifier)

        birthdayGreeter.sendGreetings(john.birthDate)

        verify(exactly = 1) { birthdayGreetingNotifier.sendBirthdayGreeting(john) }
    }

    test("should not notify when list has multiple employees with no birthday on specified day") {
        // Secondary Adapters
        val employeeRepository = mockk<EmployeeRepository>() // stub
        every { employeeRepository.getEmployees() } returns listOf(john, abby)

        val birthdayGreetingNotifier = mockk<BirthdayGreetingNotifier>() // mock
        every { birthdayGreetingNotifier.sendBirthdayGreeting(any()) } just Runs

        // Domain
        val birthdayGreeter = BirthdayGreeter(employeeRepository, birthdayGreetingNotifier)

        birthdayGreeter.sendGreetings(LocalDate.of(2022, 10, 31))

        verify(exactly = 0) { birthdayGreetingNotifier.sendBirthdayGreeting(any()) }
    }

    test("should notify all employees with birthdays when list has multiple employees") {
        val birthday = LocalDate.of(2022, 10, 10)
        val johnWithBirthday = john.copy(birthDate = birthday)
        val abbyWithBirthday = abby.copy(birthDate = birthday)

        // Secondary Adapters
        val employeeRepository = mockk<EmployeeRepository>() // stub
        every { employeeRepository.getEmployees() } returns listOf(johnWithBirthday, abbyWithBirthday, caroline)

        val birthdayGreetingNotifier = mockk<BirthdayGreetingNotifier>() // mock
        every { birthdayGreetingNotifier.sendBirthdayGreeting(any()) } just Runs

        // Domain
        val birthdayGreeter = BirthdayGreeter(employeeRepository, birthdayGreetingNotifier)

        birthdayGreeter.sendGreetings(birthday)

        verify {
            birthdayGreetingNotifier.sendBirthdayGreeting(johnWithBirthday)
            birthdayGreetingNotifier.sendBirthdayGreeting(abbyWithBirthday)
        }
    }

    test("should notify leap year birthday if not a leap year and date is feb 28") {
        val employeeRepository = mockk<EmployeeRepository>()
        val carolineWithLeapYearBirthday = caroline.copy(birthDate = LocalDate.of(1980, 2,29)) // 1980 is leap year
        every { employeeRepository.getEmployees() } returns listOf(carolineWithLeapYearBirthday)

        val birthdayGreetingNotifier = mockk<BirthdayGreetingNotifier>()
        every { birthdayGreetingNotifier.sendBirthdayGreeting(any()) } just Runs

        val birthdayGreeter = BirthdayGreeter(employeeRepository, birthdayGreetingNotifier)
        birthdayGreeter.sendGreetings(LocalDate.of(2022, 2, 28)) // 2022 is not a leap year

        verify {
            birthdayGreetingNotifier.sendBirthdayGreeting(carolineWithLeapYearBirthday)
        }
    }

    test("should trigger birthday greeting notification when using one off birthday greeting trigger") {
        val employeeRepository = mockk<EmployeeRepository>()
        every { employeeRepository.getEmployees() } returns listOf(abby)

        val birthdayGreetingNotifier = mockk<BirthdayGreetingNotifier>()
        every { birthdayGreetingNotifier.sendBirthdayGreeting(any()) } just Runs

        val birthdayGreeter = BirthdayGreeter(employeeRepository, birthdayGreetingNotifier)

        val fakeClock = FixedClock(abby.birthDate)
        val greetingTrigger = OneOffBirthdayGreetingTrigger(fakeClock, birthdayGreeter)
        greetingTrigger.run()

        verify {
            birthdayGreetingNotifier.sendBirthdayGreeting(abby)
        }
    }
})