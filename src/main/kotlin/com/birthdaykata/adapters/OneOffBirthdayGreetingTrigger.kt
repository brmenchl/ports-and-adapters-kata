package com.birthdaykata.adapters

import com.birthdaykata.domain.BirthdayGreeter
import com.birthdaykata.domain.BirthdayGreetingTrigger
import com.birthdaykata.utils.Clock

class OneOffBirthdayGreetingTrigger(private val clock: Clock, private val birthdayGreeter: BirthdayGreeter) :
    BirthdayGreetingTrigger {
    override fun run() = birthdayGreeter.sendGreetings(clock.nowDate())
}