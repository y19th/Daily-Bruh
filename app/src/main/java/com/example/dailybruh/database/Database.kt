package com.example.dailybruh.database

import androidx.lifecycle.MutableLiveData
import com.example.dailybruh.dataclasses.Article
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase




private val database = Firebase.database("https://dailybruh-bf63c-default-rtdb.europe-west1.firebasedatabase.app/")

class Database(
    phone: String = "00000000000"
) {

    data class DataArticle(val id: String ,val header: String,val urlPhoto : String,val urlPage: String,
                           val author: String,var status: MutableLiveData<Int> = MutableLiveData(0))

    private val userReference = database.reference.child("users").child(phone)
    private val articlesReference = database.reference.child("articles")

    val nickname = MutableLiveData<String>()
    val name = MutableLiveData<String>()

    val lastArticle = MutableLiveData<Article>()
    val listOfDataArticles = MutableLiveData<MutableList<DataArticle>>()


    val articlelikes = MutableLiveData<Long>()
    val userLikes = MutableLiveData<HashMap<*,*>>()
    val totalLiked = MutableLiveData<Long>()

    init {
        totalLiked()
    }

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

    fun transactionLike(article: Article) {
        userReference.child("liked").apply {
            if (totalLiked.value != null) {
                child("total").setValue(totalLiked.value!! + 1)
            } else {
                child("total").setValue(0)
            }
            child("id${totalLiked.value}").setValue(article.id)
            articlesReference.child(article.id).child("likes")
                .setValue((articlelikes.value?.plus(1)))
        }
    }

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
    fun listOfDataArticles(hashMap: HashMap<*, *>,pos: Int): MutableLiveData<MutableList<DataArticle>> {
        if(listOfDataArticles.value == null) {
            listOfDataArticles.value = MutableList(hashMap.size - 1 ) { DataArticle("","","","","") }
        }
        articlesReference.child(hashMap["id$pos"].toString()).get()
            .addOnSuccessListener { snapshot ->
                listOfDataArticles.value?.add(
                    pos, DataArticle(
                        hashMap["id$pos"].toString(),
                        snapshot.child("title").value.toString(),
                        snapshot.child("urlPhoto").value.toString(),
                        snapshot.child("urlPage").value.toString(),
                        snapshot.child("author").value.toString()
                    )
                )
            }.addOnCompleteListener {
                listOfDataArticles.value!!.forEach{
                    it.status.value = 1
                }
            }
        return listOfDataArticles
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
              child("urlPhoto").setValue(article.image)
              child("urlPage").setValue(article.url)
              child("likes").setValue(0)
              child("commentaries").child("asd").setValue("fuck ouou")
            }
        article(article.id)
        likes(article.id)
    }
}
