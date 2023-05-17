package com.example.dailybruh.web

internal var recentRequest: Request? = null
class Request(
    _endpoint: String,
    _article: String,
    _from: String? = null,
    _to: String? = null,
    _sort: String? = null,
    _language: String? = null) {

    private val endpoint: String = _endpoint
    private var article: String = _article
    private var from: String = if(_from != null) "&from=$_from" else ""
    private var to: String = if(_to != null) "&to=$_to" else ""
    private var sort: String =  if(_sort != null) "&sortBy=$_sort" else ""
    private var language: String = if(_language != null) "&language=$_language" else ""
    internal val request get() = "$endpoint?q=$article$from$to$sort$language"


    fun changeHeader(_article: String) { article = _article }
    fun changeFrom(_from: String) { from = "&from=$_from" }
    fun changeTo(_to: String) { to = "&to=$_to" }
    fun changeSort(_sort: String) { sort = "&sortBy=$_sort"}
    fun changeLanguage(_language: String) { language = "&language=$_language" }

}