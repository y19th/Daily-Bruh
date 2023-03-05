package com.example.dailybruh.enum

enum class Sort {

    POPULARITY {
        override fun get(): String = "По популярности"
    },
    RELEVANCY {
        override fun get(): String = "По алфавиту"
    },
    PUBLISHEDAT {
        override fun get(): String = "Сначала новые"
    };

    abstract fun get() : String
}