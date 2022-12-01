package com.birthdaykata.core.ports

import arrow.core.Either
import com.birthdaykata.core.models.Employee
import com.birthdaykata.core.models.Error

interface EmployeeRepository {
    fun getEmployees(): Either<Error.Repository, List<Employee>>
}
