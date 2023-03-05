package com.example.dailybruh.web

import androidx.lifecycle.MutableLiveData

private const val LANGUAGE = "ru"
private const val SORTING = "popularity"
private const val FROM = "2023-01-01"
private const val TO = "2023-01-02"

internal val language: MutableLiveData<String> by lazy {
    MutableLiveData<String>()
}
internal val sorting: MutableLiveData<String> by lazy {
    MutableLiveData<String>()
}

internal val from: MutableLiveData<String> by lazy {
    MutableLiveData<String>()
}

internal val toDate: MutableLiveData<String> by lazy {
    MutableLiveData<String>()
}

fun language(lang: String) {
    language.value = lang
}
fun sorting(sort: String) {
    sorting.value = sort
}
fun from(fr :String) {
    from.value = fr
}
fun toDate(t: String) {
    toDate.value = t
}

fun setDefaultSettings() {
    language(LANGUAGE)
    sorting(SORTING)
    from(FROM)
    toDate(TO)
}