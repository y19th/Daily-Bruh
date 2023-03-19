package com.example.dailybruh.fragment.dialog

import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.example.dailybruh.auth.AuthOptions
import com.example.dailybruh.auth.CodeCallback
import com.example.dailybruh.auth.verify
import com.example.dailybruh.databinding.FragmentDialogProfileBinding
import com.example.dailybruh.extension.ToastLong
import com.example.dailybruh.extension.disableView
import com.example.dailybruh.extension.enableView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
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
            }

            codeButton.setOnClickListener {
                credential = PhoneAuthProvider.getCredential(callback.verificationId(),codeField.text.toString())
                authOptions.signInWithCred(credential)
                if(authOptions.user != null) {
                    ToastLong(requireContext(),"Success !!!!")
                } else {
                    ToastLong(requireContext(),"Failed!")
                }
            }

            codeField.doOnTextChanged { text, start, before, count ->
                codeLayout.error = null
                if(count !in 1..6) {
                    codeLayout.error = "!0..6"  //TODO(DELETE THIS)
                }
                if(count in 1..6) {
                    codeButton.isEnabled = true
                }
                if(count == 0)codeButton.isEnabled = false
            }

            readyButton.setOnClickListener {
                readyButton.visibility = View.GONE
                codeLayout.visibility = View.VISIBLE
                codeButton.visibility = View.VISIBLE
                view.disableView()
                authOptions.createOptions(callback,phoneField.text.toString(),requireActivity(),60L).verify()
            }
        }

        //

        //
    }
}