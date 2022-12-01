package com.birthdaykata.adapters

import arrow.core.*
import arrow.typeclasses.Semigroup
import com.birthdaykata.core.ports.EmployeeRepository
import com.birthdaykata.core.models.*
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import java.io.InputStream
import java.time.LocalDate

class FileSystemEmployeeRepository(private val filepath: String) : EmployeeRepository {
    override fun getEmployees(): Either<Error.Repository, List<Employee>> = getEmployeeCsv().flatMap {
        csvReader().readAllWithHeader(it)
            .map(::parseEmployee)
            .traverse(Semigroup.nonEmptyList(), ::identity)
            .toEither()
            .mapLeft { errors ->
                Error.Repository(
                    "Cannot parse csv file - ${
                        errors.map(ValidationError::message).joinToString("\n")
                    }"
                )
            }
    }

    private fun getEmployeeCsv(): Either<Error.Repository, InputStream> =
        javaClass.classLoader.getResourceAsStream(filepath)
            .rightIfNotNull { Error.Repository("Cannot access csv file") }

    private fun parseEmployee(stringMap: Map<String, String>): Validated<ValidationErrors, Employee> =
        Email.parse(stringMap["email"]).map { email ->
            Employee(
                FullName(stringMap["first_name"]!!, stringMap["last_name"]!!),
                LocalDate.parse(stringMap["date_of_birth"]),
                email
            )
        }
}