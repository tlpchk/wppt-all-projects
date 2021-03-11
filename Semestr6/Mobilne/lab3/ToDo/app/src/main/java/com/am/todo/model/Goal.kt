package com.am.todo.model

import java.time.LocalDate
import java.time.temporal.ChronoUnit


class Goal (val description: String, val imgRes: Int, val deadline: LocalDate, var priority: Priority = Priority.DEAD){

    companion object {
        fun daysToDeadline(deadline: LocalDate): Int {
            val today = LocalDate.now()
            val days = today.until(deadline, ChronoUnit.DAYS)

            return days.toInt()
        }
    }

    fun refreshPriority() {
        val days = daysToDeadline(deadline)

        priority = when {
            days < 0 -> Priority.DEAD
            days == 0 -> Priority.CRITICAL
            days in 1..3 -> Priority.HIGH
            days in 4..7 -> Priority.MEDIUM
            days in 7..14 -> Priority.LOW
            else -> Priority.VERY_LOW
        }
    }
}