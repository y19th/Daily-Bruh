package com.example.dailybruh.web

import android.content.Context
import android.icu.util.Calendar
import com.example.dailybruh.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

sealed class Language {

    abstract val language: String
    class Russian(val context: Context) : Language() {
        override val language: String
            get() = context.getString(R.string.russian)
    }
    class English(val context: Context) : Language() {
        override val language: String
            get() = context.getString(R.string.english)
    }
}

sealed class From {
    abstract val getString: String
    abstract val getTime: String

    private val dateFormat = "yyyy-MM-dd"

    class Today(val context: Context) : From() {
        override val getString: String
            get() = context.getString(R.string.from_today)
        override val getTime: String
            get() = getTime(-1)
    }

    class Week(val context: Context) : From() {
        override val getString: String
            get() = context.getString(R.string.from_week)
        override val getTime: String
            get() = getTime(-7)
    }

    class Month(val context: Context) : From() {
        override val getString: String
            get() = context.getString(R.string.from_month)
        override val getTime: String
            get() = getTime()
    }

    protected fun getTime(days: Int): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR,days)
        return SimpleDateFormat(dateFormat, Locale.getDefault()).format(Date(calendar.timeInMillis))
    }
    protected fun getTime(): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR,-(calendar.getActualMaximum(Calendar.DAY_OF_MONTH)))
        return SimpleDateFormat(dateFormat, Locale.getDefault()).format(Date(calendar.timeInMillis))
    }
}

sealed class Sort {
    abstract val getString: String
    abstract val getParam: String

    class Popularity(val context: Context) : Sort() {
        override val getString: String
            get() = context.getString(R.string.sort_popularity_string)
        override val getParam: String
            get() = context.getString(R.string.sort_popularity_param)

    }

    class Relevancy(val context: Context) : Sort() {
        override val getString: String
            get() = context.getString(R.string.sort_relevancy_string)
        override val getParam: String
            get() = context.getString(R.string.sort_relevancy_param)

    }

    class PublishedAt(val context: Context) : Sort() {
        override val getString: String
            get() = context.getString(R.string.sort_publishedat_string)
        override val getParam: String
            get() = context.getString(R.string.sort_publishedat_param)

    }

}