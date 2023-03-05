package com.example.dailybruh.web


class Request(
    _endpoint: String,
    _article: String,
    _from: String? = null,
    _to: String? = null,
    _sort: String? = null,
    _language: String? = null) {

    private val endpoint: String = _endpoint
    private val article: String = _article
    private val from: String = if(_from != null) "&from=$_from" else ""
    private val to: String = if(_to != null) "&to=$_to" else ""
    private val sort: String =  if(_sort != null) "&sortBy=$_sort" else ""
    private val language: String = if(_language != null) "&language=$_language" else ""
    internal val request = "$endpoint?q=$article$from$to$sort$language"

}