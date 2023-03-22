package com.example.dailybruh.database

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


private val database = Firebase.database("https://dailybruh-bf63c-default-rtdb.europe-west1.firebasedatabase.app/")

class Database(phone: String) {

    private val userReference = database.reference.child("users").child(phone)
    private val user = Firebase.auth.currentUser

    val nickname = MutableLiveData<String>()
    val name = MutableLiveData<String>()



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

    fun nickname(nick: String) {
        userReference.child("nickname").setValue(nick)
    }
    fun name(name: String) {
        userReference.child("name").setValue(name)
    }
}
