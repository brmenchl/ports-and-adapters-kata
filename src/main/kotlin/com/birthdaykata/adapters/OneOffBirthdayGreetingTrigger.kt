package com.birthdaykata.adapters

import com.birthdaykata.usecases.BirthdayGreeter
import com.birthdaykata.domain.BirthdayGreetingTrigger
import com.birthdaykata.domain.models.Error
import com.birthdaykata.utils.Clock

class OneOffBirthdayGreetingTrigger(private val clock: Clock, private val birthdayGreeter: BirthdayGreeter) :
    BirthdayGreetingTrigger {
    override fun run() = birthdayGreeter.sendGreetings(clock.nowDate()).fold({ error ->
        when (error) {
            is Error.Repository -> println(error.msg)
        }
    }, {})
}