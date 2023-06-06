package com.example.dailybruh.fragment.auth

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.core.view.children
import androidx.core.widget.doOnTextChanged
import com.example.dailybruh.R
import com.example.dailybruh.databinding.FragmentAuthPhoneBinding
import com.example.dailybruh.extension.makeGone
import com.example.dailybruh.extension.makeVisible
import com.example.dailybruh.extension.navigateTo
import com.example.dailybruh.fragment.StandardFragment
import com.example.dailybruh.interfaces.auth.AuthPhoneView
import com.example.dailybruh.presenter.AuthPhonePresenter

class FragmentAuthPhone : StandardFragment<FragmentAuthPhoneBinding>(), AuthPhoneView {
    override val viewContext: Context
        get() = requireContext()

    override val viewFragment: FragmentAuthPhone
        get() = this


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthPhoneBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val presenter = AuthPhonePresenter(
            viewState = this@FragmentAuthPhone
        ).also { it.setCallback() }

        binding.apply {
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
                        presenter.createOptions(phoneField.text.toString())
                    }
                    0 -> { error("Необходимо ввести номер",true) }
                    in 1..15 -> { error("Номер должен состоять из 11 цифр",true) }
                    else -> { error("Слишком большой номер",true) }
                }
            }
            backButton.backButtonLayout.setOnClickListener {
                view.navigateTo(R.id.auth_phone_to_newspage)
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
                    }.makeVisible()
                }
                false -> {
                    phoneErrorText.apply {
                        text = null
                    }.makeGone()
                }
            }
        }
    }



    override fun navigateNext(name: String,codeView: View) {
        when(name) {
            "null" -> codeView.navigateTo(R.id.auth_vercode_to_auth_name)
            else -> codeView.navigateTo(R.id.auth_vercode_to_profile)
        }
    }
}