package com.example.dailybruh.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dailybruh.R
import com.example.dailybruh.databinding.FragmentProfileBinding
import com.example.dailybruh.extension.navigateTo
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FragmentProfile : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding: FragmentProfileBinding get() = _binding!!
    private val user = Firebase.auth.currentUser

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
            when(user) {
                null ->  {
                    phoneField.text = "have no number"
                    nameField.text = "have no name"
                }
                else -> {
                    phoneField.text = user.phoneNumber
                    nameField.text = "Олегофрен"
                }
            }
            backButtonLayout.setOnClickListener {
                view.navigateTo(R.id.profile_to_newspage,requireArguments())
            }
            signOutButton.setOnClickListener {
                //user?.let { Firebase.auth.signOut() } // need if app has anonymous auth
                Firebase.auth.signOut()
                view.navigateTo(R.id.profile_to_newspage,requireArguments())
            }
        }
    }
}