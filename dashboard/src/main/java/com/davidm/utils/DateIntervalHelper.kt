package com.davidm.utils

import com.davidm.entities.DateInterval
import java.text.SimpleDateFormat
import java.util.*

class DateIntervalHelper {

    fun generateDateIntervalList(): List<DateInterval> {
        val calendar = Calendar.getInstance()

        // the string format that APIs want
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val list = listOf<DateInterval>().toMutableList()


        for (i in Calendar.JANUARY..Calendar.DECEMBER) {

            // here the i variable is the month index so we can set the last day and first day date
            calendar.run {
                set(Calendar.MONTH, i)
                set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
            }

            // take the last day of the current month
            val endDate: Date = calendar.time

            calendar.set(Calendar.DAY_OF_MONTH, 1)
            // take the first day of the current month
            val startDate: Date = calendar.time

            list.add(
                DateInterval(
                    dateFormat.format(startDate),
                    dateFormat.format(endDate),
                    calendar.get(Calendar.MONTH)
                )
            )
        }
        return list
    }

}