package com.example.dailybruh.fragment.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.example.dailybruh.R
import com.example.dailybruh.auth.AuthOptions
import com.example.dailybruh.auth.CodeCallback
import com.example.dailybruh.auth.verify
import com.example.dailybruh.databinding.FragmentDialogProfileBinding
import com.example.dailybruh.extension.*
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class FragmentDialogProfile : BottomSheetDialogFragment() {

    private var _binding: FragmentDialogProfileBinding? = null
    private val binding: FragmentDialogProfileBinding get() = _binding!!
    private lateinit var authOptions: AuthOptions
    private lateinit var credential: PhoneAuthCredential

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDialogProfileBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            authOptions = AuthOptions(requireContext())

            val callback = object : CodeCallback(requireContext()) {
                override fun stepOnCodeSent() {
                    Toast.makeText(context,"Code sent",Toast.LENGTH_LONG).show()
                    view.enableView()
                    phoneLayout.isEnabled = false
                }

                override fun onSuccessAuth() {
                    parentFragment?.view?.navigateTo(R.id.newspage_to_profile, parentFragment!!.requireArguments())
                    ToastLong(requireContext(),"Success !!!!")
                }

                override fun onFailedAuth() {
                    codeButton.isEnabled = true
                    codeLayout.error = "Неверный код"
                    ToastLong(requireContext(),"Failed!")
                }
            }

            codeButton.setOnClickListener {
                codeButton.isEnabled = false
                credential = PhoneAuthProvider.getCredential(callback.verificationId(),codeField.text.toString())
                authOptions.signInWithCred(credential)
            }

            codeField.doOnTextChanged { _, _, _, _ ->
                codeLayout.error = null
                codeButton.isEnabled = true
                if(codeField.text?.length == 0) {
                    codeButton.isEnabled = false
                    codeLayout.error = "Код не может быть пустым"
                }
            }

            readyButton.setOnClickListener {
                readyButton.visibility = View.GONE
                codeLayout.visibility = View.VISIBLE
                codeButton.visibility = View.VISIBLE
                view.disableView()
                authOptions.createOptions(callback,phoneField.text.toString(),requireActivity(),60L).verify()
            }
        }
    }
}