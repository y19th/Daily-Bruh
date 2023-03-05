package com.example.dailybruh.enum

enum class Language {
    RUSSIAN {
        override fun get(): String = "Русский"
    },
    ENGLISH {
        override fun get(): String = "Английский"
    };

    abstract fun get(): String
}