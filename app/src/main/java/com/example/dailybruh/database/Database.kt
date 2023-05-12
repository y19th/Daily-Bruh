package com.example.dailybruh.database

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.example.dailybruh.const.STANDARD_PHONE
import com.example.dailybruh.dataclasses.Article
import com.example.dailybruh.dataclasses.ArticleLikes
import com.example.dailybruh.dataclasses.DataArticle
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


private val database = Firebase.database("https://dailybruh-bf63c-default-rtdb.europe-west1.firebasedatabase.app/")

class Database(
    _phone: String = STANDARD_PHONE,
    private var lifecycleOwner: LifecycleOwner? = null,
    private var lifecycle: Lifecycle? = null
) {

    val userReference = database.reference.child("users").child(_phone)
    val dataReference = database.reference.child("articles")
    val phone = _phone

    val name = MutableLiveData<String>()


    val likes = MutableLiveData<ArticleLikes>()

    val userLikes = MutableLiveData<HashMap<*,*>>()
    val totalLiked = MutableLiveData<Long>()

//    private val singleThread = Executors.newSingleThreadExecutor().asCoroutineDispatcher()


    init {
        if(phone != STANDARD_PHONE) {
            createLiked()
            userLikes()
        }
    }
    fun setLifecycle(lOwner: LifecycleOwner? = null,lcycle: Lifecycle? = null) {
        lifecycleOwner = lOwner?.let { lOwner }
        lifecycle = lcycle?.let { lcycle }
    }

    // user scope
    //

    fun changeNickname(nick: String) {
        userReference.child("nickname").setValue(nick)
    }
    fun changeName(name: String) {
        userReference.child("name").setValue(name)
    }

    private fun createLiked() {
        userReference.child("liked").get().addOnSuccessListener {
            if(it.value == null) userReference.child("liked").child("total").setValue(0)
            else totalLiked()
        }
    }
    //


    // Data scope
    //

    fun userLikes(): MutableLiveData<HashMap<*, *>> {
        userReference.child("liked").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userLikes.value = snapshot.value as HashMap<*, *>
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
        return userLikes
    }
    fun totalLiked(): MutableLiveData<Long> {
        userReference.child("liked").child("total").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                totalLiked.value = snapshot.value as Long
            }
            override fun onCancelled(error: DatabaseError) {
            }

        })

        return totalLiked
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

    fun getArticleByPos(pos: Int): MutableLiveData<DataArticle> {
        val article = MutableLiveData<DataArticle>()
        dataReference.child(userLikes.value!!["id$pos"].toString()).get().addOnSuccessListener { snapshot ->
            article.value = DataArticle(
                "id${pos}",
                snapshot.child("title").value.toString(),
                snapshot.child("urlPhoto").value.toString(),
                snapshot.child("urlPage").value.toString(),
                snapshot.child("author").value.toString()
            )
        }
        return article
    }


    private fun likes(id : String): MutableLiveData<ArticleLikes> {
        dataReference.child(id).child("likes").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                likes.value = ArticleLikes(id,snapshot.value as Long)
            }

            override fun onCancelled(error: DatabaseError) {
                likes.value?.status?.value = -1
            }

        })
        return likes
    }

    fun article(article: Article) {
        dataReference.child(article.id).get().addOnSuccessListener {
            when(it.value) {
                null -> standardParams(article)
                else -> {
                    likes(article.id)
                    likes.value = ArticleLikes(article.id,(it.value as HashMap<*,*>)["likes"] as Long)
                    likes.value!!.status.value = 1
                }
            }
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

    //
}
