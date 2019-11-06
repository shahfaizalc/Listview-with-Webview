package com.news.list.utils

import android.content.Context
import com.news.list.R
import com.news.list.injection.Application
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Class to handle date
 */
class DateFormatHandler {

    @Inject
    lateinit var context: Context

    init {
        Application.component.inject(this)
    }

    /**
     * function to convert date into specific format
     */
    fun timeIndays(unitDate: String, timeUnit: TimeUnits): String {
        var unitTime = unitDate;
        try {
            val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
            val past = format.parse(unitTime)
            val now = Date()
            when (timeUnit) {
                TimeUnits.DAYS -> {
                    val unit = TimeUnit.MILLISECONDS.toDays(now.getTime() - past.getTime())
                    unitTime = resultInDays(unit)
                }
            }
        } catch (j: Exception) {
            j.printStackTrace()
        }
        return unitTime;
    }


    private fun resultInDays(unit: Long): String {
        var dateValue = ""
        if (unit > 0 && unit < 2) {
            dateValue = unit.toString() + context.resources.getString(R.string.oneday)
        } else if (unit > 1) {
            dateValue = unit.toString() + context.resources.getString(R.string.moredays)
        } else {
            dateValue = context.resources.getString(R.string.today)
        }
        return dateValue
    }
}