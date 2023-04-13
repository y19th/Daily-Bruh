package com.example.dailybruh.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dailybruh.R
import com.example.dailybruh.const.STANDARD_PHONE
import com.example.dailybruh.database.Database
import com.example.dailybruh.database.constDatabase
import com.example.dailybruh.databinding.FragmentProfileBinding
import com.example.dailybruh.extension.navigateTo
import com.example.dailybruh.fragment.dialog.profile.FragmentDialogProfileName
import com.example.dailybruh.fragment.dialog.profile.FragmentDialogProfileNickname
import com.example.dailybruh.fragment.dialog.profile.FragmentDialogProfileSavedArticles
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FragmentProfile : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding: FragmentProfileBinding get() = _binding!!
    private val user = Firebase.auth.currentUser
    private val database = if(constDatabase.value!!.phone == STANDARD_PHONE)Database().newInstance(user?.phoneNumber!!)
                           else constDatabase.value!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            phoneField.text = user?.phoneNumber
            backButtonLayout.setOnClickListener {
                view.navigateTo(R.id.profile_to_newspage)
            }
            signOutButton.setOnClickListener {
                Firebase.auth.signOut()
                view.navigateTo(R.id.profile_to_newspage)
            }
            nameLayout.setOnClickListener {
                FragmentDialogProfileName(database).show(childFragmentManager,"name_edit_dialog")
            }
            nicknameLayout.setOnClickListener {
                FragmentDialogProfileNickname(database).show(childFragmentManager,"nickname_edit_dialog")
            }
            savedNewsLayout.setOnClickListener {
                FragmentDialogProfileSavedArticles(database).show(childFragmentManager,"saved_articles_dialog")
            }
            likedNewsLayout.setOnClickListener {
                view.navigateTo(R.id.profile_to_liked_articles)
            }
            database.apply {
                nickname().observe(viewLifecycleOwner) {
                    nicknameField.text = it ?: "user"
                }
                name().observe(viewLifecycleOwner) {
                    nameField.text = it ?: "no name"
                }
            }
        }
    }
}