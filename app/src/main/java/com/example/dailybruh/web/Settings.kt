package com.example.dailybruh.web

import androidx.lifecycle.MutableLiveData
import com.example.dailybruh.enum.From
import com.example.dailybruh.enum.Sort

private const val LANGUAGE = "ru"
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

fun language(lang: String): String? {
    language.value = lang
    return language.value
}
fun sorting(sort: String): String? {
    sorting.value = sort
    return sorting.value
}
fun from(fr :String): String? {
    from.value = fr
    return from.value
}
fun toDate(t: String): String? {
    toDate.value = t
    return toDate.value
}

fun setDefaultSettings() {
    language(LANGUAGE)
    sorting(Sort.POPULARITY.get())
    from(From.FROM_MONTH.get())
    toDate(TO)
}