package com.example.dailybruh.fragment.auth

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.core.view.children
import androidx.core.widget.doOnTextChanged
import com.example.dailybruh.R
import com.example.dailybruh.auth.AuthOptions
import com.example.dailybruh.const.AUTH_OPTIONS
import com.example.dailybruh.const.VERIFICATION_ID
import com.example.dailybruh.databinding.FragmentAuthVercodeBinding
import com.example.dailybruh.extension.navigateTo
import com.example.dailybruh.fragment.StandardFragment
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class FragmentAuthVerCode: StandardFragment<FragmentAuthVercodeBinding>() {

    private lateinit var credential: PhoneAuthCredential
    private lateinit var verId: String
    private lateinit var authOptions: AuthOptions


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthVercodeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
       verId = requireArguments().getString(VERIFICATION_ID)!!
        authOptions = if(Build.VERSION.SDK_INT >= 33) {
            requireArguments().getSerializable(AUTH_OPTIONS,AuthOptions::class.java)!!
        } else {
            requireArguments().getSerializable(AUTH_OPTIONS) as AuthOptions
        }
        binding.apply {


            codeField.doOnTextChanged { _, _, _, _ ->
                error(enabled = false)
            }

            continueButton.setOnClickListener {
                when(codeField.text.length) {
                    in 0..5 -> {
                        error("Слишком мало символов", true)
                    }
                    else -> {
                        authOptions.setCurrentSetUp(view, viewLifecycleOwner)
                        credential = PhoneAuthProvider.getCredential(verId,codeField.text.toString())
                        authOptions.signInWithCred(credential)
                    }
                }
            }
            backButton.backButtonLayout.setOnClickListener {
                view.navigateTo(R.id.auth_vercode_to_auth_phone)
            }
            keyboardGridLayout.children.forEach {
                it.setOnClickListener(onClickListener(it.tag.toString()))
            }
            keyboardButtonErase.setOnClickListener {
                codeField.apply { text = "${text?.dropLast(1)}" }
            }
        }
    }
    private fun onClickListener(tag : String): OnClickListener {
        return OnClickListener {
            binding.codeField.apply {
                if (editableText != null) editableText.append(tag)
                else text = tag
            }
        }
    }
    private fun error(message: String = "",enabled: Boolean) {
        binding.apply {
            when (enabled) {
                true -> {
                    codeErrorText.apply {
                        text = message
                    }.visibility = View.VISIBLE
                }
                false -> {
                    codeErrorText.apply {
                        text = null
                    }.visibility = View.GONE
                }
            }
        }
    }
}