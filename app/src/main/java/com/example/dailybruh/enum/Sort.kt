package com.example.dailybruh.enum

enum class Sort {

    POPULARITY {
        override fun get(): String = "популярные"
        override fun getParam(): String = "popularity"
    },
    RELEVANCY {
        override fun get(): String = "самые актуальные"
        override fun getParam(): String = "relevancy"
    },
    PUBLISHEDAT {
        override fun get(): String = "новейшие"
        override fun getParam(): String = "publishedAt"
    };

    abstract fun get() : String
    abstract fun getParam() : String
}