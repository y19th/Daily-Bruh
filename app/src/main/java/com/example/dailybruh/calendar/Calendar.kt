package com.example.dailybruh.calendar

fun parseDate(string: String?): String {
    string?.let {
        val fString: String
        val yDate: String = it.substring(0, it.indexOf("T"))
        val dDate: String = it.substring(it.indexOf("T") + 1,it.indexOf("Z") - 3)
        fString = "${yearString(yDate)} в $dDate"
        return fString
    }
    return "null date"
}

private fun yearString(string: String): String {
    val day = string.substring(IntRange(8,9))
    val month = string.substring(IntRange(5,6))
    val year = string.substring(IntRange(0,3))
    return "$day ${monthToCal(month)} $year года"
}

fun monthToCal(string: String): String {
    return when(string) {
        "01","1" -> {
            "января"
        }
        "02","2" -> {
            "февраля"
        }
        "03","3" -> {
            "марта"
        }
        "04","4" -> {
            "апреля"
        }
        "05","5" -> {
            "мая"
        }
        "06","6" -> {
            "июня"
        }
        "07","7" -> {
            "июля"
        }
        "08","8" -> {
            "августа"
        }
        "09","9" -> {
            "сентября"
        }
        "10" -> {
            "октября"
        }
        "11" -> {
            "ноября"
        }
        "12" -> {
            "декабря"
        }
        else -> {
            "null date"
        }
    }

}