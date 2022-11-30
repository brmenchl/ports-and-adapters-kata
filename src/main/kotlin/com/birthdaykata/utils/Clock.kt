package com.birthdaykata.utils

import java.time.LocalDate

interface Clock {
    fun nowDate(): LocalDate
}

class SystemClock : Clock {
    override fun nowDate(): LocalDate = LocalDate.now()
}

class FixedClock(private val date: LocalDate) : Clock {
    override fun nowDate() = date
}