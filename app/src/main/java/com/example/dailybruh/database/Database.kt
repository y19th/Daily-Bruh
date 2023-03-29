package com.example.dailybruh.database

import androidx.lifecycle.MutableLiveData
import com.example.dailybruh.dataclasses.Article
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
    private val articlesReference = database.reference.child("articles")
    private val user = Firebase.auth.currentUser

    val nickname = MutableLiveData<String>()
    val name = MutableLiveData<String>()
    val news = MutableLiveData<News>()
    val lastArticle = MutableLiveData<Article>()
    val articlelikes = MutableLiveData<Long>()
    val userLikes = MutableLiveData<String>()



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
    fun userLikes(): MutableLiveData<String> {
        userReference.child("liked").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userLikes.value = snapshot.value as String
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }




    //useless
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
    //

    fun article(id : String): MutableLiveData<Article> {
        articlesReference.child(id).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //lastArticle.value = snapshot.value as Article
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
        return lastArticle
    }

    fun likes(id : String): MutableLiveData<Long> {
        articlesReference.child(id).child("likes").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                articlelikes.value = snapshot.value as Long
            }

            override fun onCancelled(error: DatabaseError) {
                articlelikes.value = -1
            }

        })
        return articlelikes
    }

    fun article(article: Article) {
            articlesReference.child(article.id).apply {
              child("title").setValue(article.title!!)
              child("author").setValue(article.author)
              child("likes").setValue(0)
              child("commentaries").child("asd").setValue("fuck ouou")
            }
        article(article.id)
        likes(article.id)
    }
}
