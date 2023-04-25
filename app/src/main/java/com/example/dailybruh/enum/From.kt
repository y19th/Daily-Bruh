package com.example.dailybruh.enum

import android.icu.util.Calendar
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

enum class From {

    FROM_TODAY {
        override fun get(): String {
            return "за сегодня"
        }

        override fun getParam(): String {
            return getTime(-1)
        }

    },
    FROM_WEEK {
        override fun get(): String {
            return "за неделю"
        }

        override fun getParam(): String {
            return getTime(-7)
        }

    },
    FROM_MONTH {
        override fun get(): String {
            return "за месяц"
        }

        override fun getParam(): String {
            return getTime()
        }

    },
    DATE_FORMAT {
        override fun get(): String {
            return "yyyy-MM-dd"
        }

        override fun getParam(): String {
            return get()
        }
    };

    abstract fun get() : String
    abstract fun getParam() : String

    protected fun getTime(days: Int): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR,days)
        return SimpleDateFormat(DATE_FORMAT.get(), Locale.getDefault()).format(Date(calendar.timeInMillis))
    }
    protected fun getTime(): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR,-(calendar.getActualMaximum(Calendar.DAY_OF_MONTH)))
        return SimpleDateFormat(DATE_FORMAT.get(), Locale.getDefault()).format(Date(calendar.timeInMillis))
    }
}