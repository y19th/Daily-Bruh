package com.example.dailybruh.database

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.example.dailybruh.const.STANDARD_PHONE
import com.example.dailybruh.dataclasses.Article
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


private val database = Firebase.database("https://dailybruh-bf63c-default-rtdb.europe-west1.firebasedatabase.app/")

class Database(
    phone: String = STANDARD_PHONE,
    private var lifecycleOwner: LifecycleOwner? = null,
    private var lifecycle: Lifecycle? = null
) {

    val userReference = database.reference.child("users").child(phone)
    val dataReference = database.reference.child("articles")
    val name = MutableLiveData<String>()

    fun setLifecycle(lOwner: LifecycleOwner? = null, lcycle: Lifecycle? = null) {
        lifecycleOwner = lOwner?.let { lOwner }
        lifecycle = lcycle?.let { lcycle }
    }
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
            child("commentaries").child("asd").setValue("fuck ouou")
        }
    }
}
