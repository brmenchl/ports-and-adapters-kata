package com.birthdaykata.adapters

import com.birthdaykata.domain.EmployeeRepository
import com.birthdaykata.domain.models.EmailAddress
import com.birthdaykata.domain.models.Employee
import com.birthdaykata.domain.models.FullName
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import java.time.LocalDate


class EmployeeFileSystemRepository(private val filepath: String) : EmployeeRepository {
    override fun getEmployees(): List<Employee> = javaClass.classLoader.getResourceAsStream(filepath)!!.let {
        csvReader().readAllWithHeader(it).map(::parseEmployee)
    }

    private fun parseEmployee(stringMap: Map<String, String>): Employee = Employee(
        FullName(stringMap["first_name"]!!, stringMap["last_name"]!!),
        LocalDate.parse(stringMap["date_of_birth"]),
        EmailAddress.fromString(stringMap["email"]!!)
    )
}