package com.example.dailybruh.database

import androidx.lifecycle.MutableLiveData
import com.example.dailybruh.const.STANDARD_PHONE
import com.example.dailybruh.dataclasses.Article
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase



class Database(phone: String = STANDARD_PHONE) {

    private val firebase = Firebase.database("https://dailybruh-bf63c-default-rtdb.europe-west1.firebasedatabase.app/")

    val userReference = firebase.reference.child("users").child(phone)
    val dataReference = firebase.reference.child("articles")
    val name = MutableLiveData<String>()

    fun changeNickname(nick: String) {
        userReference.child("nickname").setValue(nick)
    }
    fun changeName(name: String) {
        userReference.child("name").setValue(name)
    }
    fun addLike(itemId: String, value: String,total: Long) {
        userReference.child("liked").also {
          it.child("total").setValue(total)
        }.child(itemId).setValue(value)
    }

    fun removeLike(itemId: String, total: Long) {
        userReference.child("liked").also {
            it.child("total").setValue(total)
        }.child(itemId).removeValue()
    }

    fun addSave(itemId: String,value: String,total: Long) {
        userReference.child("saved").also {
            it.child("total").setValue(total)
        }.child(itemId).setValue(value)
    }

    fun removeSave(itemId: String,total: Long) {
        userReference.child("saved").also {
            it.child("total").setValue(total)
        }.child(itemId).removeValue()
    }

    fun setZeroParam(idChild: String) {
        userReference.child(idChild).child("total").setValue(0L)
    }
    fun article(article: Article) {
        dataReference.child(article.id).get().addOnCompleteListener {
            if(it.result.value == null)standardParams(article)
        }
    }

    private fun standardParams(article: Article) {
        dataReference.child(article.id).apply {
            child("title").setValue(article.title!!)
            child("author").setValue(article.author)
            child("urlPhoto").setValue(article.image)
            child("urlPage").setValue(article.url)
            child("likes").setValue(0)
            child("description").setValue(article.desc)
            child("commentaries").child("asd").setValue("fuck ouou")
        }
    }
}
