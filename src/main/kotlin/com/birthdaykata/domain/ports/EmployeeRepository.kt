package com.birthdaykata.domain.ports

import arrow.core.Either
import com.birthdaykata.domain.models.Employee
import com.birthdaykata.domain.models.Error

interface EmployeeRepository {
    fun getEmployees(): Either<Error.Repository, List<Employee>>
}
