package com.example.dailybruh.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dailybruh.R
import com.example.dailybruh.databinding.FragmentProfileBinding
import com.example.dailybruh.extension.navigateTo
import com.example.dailybruh.fragment.dialog.profile.FragmentDialogProfileName
import com.example.dailybruh.fragment.dialog.profile.FragmentDialogProfileNickname
import com.example.dailybruh.interfaces.profile.ProfileView
import com.example.dailybruh.presenter.ProfilePresenter
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FragmentProfile : StandardFragment<FragmentProfileBinding>(), ProfileView {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val presenter = ProfilePresenter(viewState = this, database = database).also { it.loadData() }

        binding.apply {
            phoneField.text = presenter.phoneNumber
            backButton.backButtonLayout.setOnClickListener {
                view.navigateTo(R.id.profile_to_newspage)
            }
            signOutButton.setOnClickListener {
                Firebase.auth.signOut()
                view.navigateTo(R.id.profile_to_newspage)
            }
            nameLayout.setOnClickListener {
                FragmentDialogProfileName(
                    database, update = { newText ->
                        nameField.text = newText
                    }
                ).show(childFragmentManager,"name_edit_dialog")
            }
            nicknameLayout.setOnClickListener {
                FragmentDialogProfileNickname(
                    database, update = { newText ->
                        nicknameField.text = newText
                    }
                ).show(childFragmentManager,"nickname_edit_dialog")
            }
            savedNewsLayout.setOnClickListener {
                view.navigateTo(R.id.profile_to_saved_articles)
            }
            likedNewsLayout.setOnClickListener {
                view.navigateTo(R.id.profile_to_liked_articles)
            }
        }
    }

    override fun setName(result: String) {
        binding.nameField.text = result
    }

    override fun setNickname(result: String) {
        binding.nicknameField.text = result
    }
}