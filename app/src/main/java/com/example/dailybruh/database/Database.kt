package com.example.dailybruh.database

import androidx.lifecycle.MutableLiveData
import com.example.dailybruh.dataclasses.News
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase




private val database = Firebase.database("https://dailybruh-bf63c-default-rtdb.europe-west1.firebasedatabase.app/")

class Database(phone: String = "00000000000") {

    private val userReference = database.reference.child("users").child(phone)
    private val user = Firebase.auth.currentUser

    val nickname = MutableLiveData<String>()
    val name = MutableLiveData<String>()
    val news = MutableLiveData<News>()



    fun nickname(): MutableLiveData<String> {
        userReference.child("nickname").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                nickname.value = snapshot.value as String?
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
        return nickname
    }

    fun nickname(nick: String) {
        userReference.child("nickname").setValue(nick)
    }

    fun name(): MutableLiveData<String> {
        userReference.child("name").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                name.value = snapshot.value as String?
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
        return name
    }

    fun name(name: String) {
        userReference.child("name").setValue(name)
    }

    fun news(): MutableLiveData<News> {
        userReference.child("articles").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                    news.value = snapshot.value as News
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
        return news
    }
    fun news(articles: News) {
        for(i in 0..100) {
            val article = database.reference.child("articles").child(articles.articles[i].title!!)
            article.child("id").setValue(articles.articles[i].id)
            article.child("author").setValue(articles.articles[i].author)
            article.child("description").setValue(articles.articles[i].desc)
            article.child("likes").setValue(0)
        }
    }
}
