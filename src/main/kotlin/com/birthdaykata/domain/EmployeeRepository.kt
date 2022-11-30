package com.birthdaykata.domain

import com.birthdaykata.domain.models.Employee

interface EmployeeRepository {
    fun getEmployees(): List<Employee>
}
