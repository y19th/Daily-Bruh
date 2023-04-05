package com.example.dailybruh.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.core.view.children
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.example.dailybruh.R
import com.example.dailybruh.auth.AuthOptions
import com.example.dailybruh.auth.CodeCallback
import com.example.dailybruh.auth.verify
import com.example.dailybruh.const.AUTH_OPTIONS
import com.example.dailybruh.const.VERIFICATION_ID
import com.example.dailybruh.databinding.FragmentAuthPhoneBinding
import com.example.dailybruh.extension.*
import com.google.firebase.auth.PhoneAuthCredential

class FragmentAuthPhone : Fragment() {

    private var _binding: FragmentAuthPhoneBinding? = null
    private val binding: FragmentAuthPhoneBinding get() = _binding!!
    private lateinit var authOptions: AuthOptions


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthPhoneBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            authOptions = AuthOptions(requireContext())

            val callback = object : CodeCallback(requireContext()) {
                override fun stepOnCodeSent() {
                    ToastLong(requireContext(),"Code sent")
                    view.enableView()
                    requireArguments().apply {
                        putSerializable(AUTH_OPTIONS,authOptions)
                    }.putString(VERIFICATION_ID,this.verificationId())
                    view.navigateTo(R.id.auth_phone_to_auth_vercode,requireArguments())

                }
                override fun onSuccessAuth(
                    view: View,
                    navigationId: Int,
                    arguments: Bundle) {
                    view.navigateTo(navigationId, arguments)
                    ToastLong(requireContext(), "Success !!!!")
                }

                override fun onFailedAuth() {
                    ToastLong(requireContext(),"Failed!")
                }
            }

            phoneField.doOnTextChanged { text, start, _, end ->
                error(enabled = false)
                phoneField.apply {
                    if (text?.length == 1 && text[0] == '8') {
                        setText("+7")
                    }
                    when (this.text?.length) {
                        3, 7, 11, 14 -> if (end != 0 && start != 0) phoneField.editableText.insert(start,"-")
                    }
                }
            }
            continueButton.setOnClickListener {
                when(phoneField.text!!.length) {
                    16 -> {
                        view.disableView()
                        authOptions.createOptions(
                            callback,
                            phoneField.text.toString(),
                            requireActivity(),
                            60L
                        ).verify()
                    }
                    0 -> { error("Необходимо ввести номер",true) }
                    in 1..15 -> { error("Номер должен состоять из 11 цифр",true) }
                    else -> { error("Слишком большой номер",true) }
                }
            }
            backButtonLayout.setOnClickListener {
                view.navigateTo(R.id.auth_phone_to_newspage,requireArguments())
            }
            keyboardGridLayout.children.forEach {
                it.setOnClickListener(onClickListener(it.tag.toString()))
            }
            keyboardButtonErase.setOnClickListener {
                phoneField.apply { text = "${text?.dropLast(1)}" }
            }
        }
    }

    private fun onClickListener(tag : String): OnClickListener {
        return OnClickListener {
            binding.phoneField.apply {
                if(editableText != null)editableText.append(tag)
                else text = tag
            }
        }
    }
    private fun error(message: String = "",enabled: Boolean) {
        binding.apply {
            when (enabled) {
                true -> {
                    phoneErrorText.apply {
                        text = message
                    }.visibility = View.VISIBLE
                }
                false -> {
                    phoneErrorText.apply {
                        text = null
                    }.visibility = View.GONE
                }
            }
        }
    }
}