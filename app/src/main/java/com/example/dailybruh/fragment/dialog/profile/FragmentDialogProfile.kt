package com.example.dailybruh.fragment.dialog.profile

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.doOnAttach
import androidx.core.widget.doBeforeTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.DialogFragment
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

            phoneField.doOnTextChanged { text,start,_,end ->
                phoneLayout.error = null
                phoneField.apply {
                    if(text?.length == 1 && text[0] == '8') {
                        setText("+7")
                        setSelection(this.text!!.length)
                    }
                    when(this.text?.length) {
                            3,7,11,14 -> if(end != 0)phoneField.text?.insert(start,"-")
                        }
                    readyButton.text = text?.length.toString()
                }
            }

            readyButton.setOnClickListener {

                val leng = phoneField.text?.length

                if(leng == 16) {
                    view.disableView()
                    readyButton.visibility = View.INVISIBLE
                    codeLayout.visibility = View.VISIBLE
                    codeButton.visibility = View.VISIBLE
                    authOptions.createOptions(
                        callback,
                        phoneField.text.toString(),
                        requireActivity(),
                        60L
                    ).verify()
                } else {
                    if(leng == 0 || leng == null)phoneLayout.error = "Номер не может быть пустым"
                    else if (leng > 16)phoneLayout.error = "Слишком большой номер"
                    else if (leng < 16)phoneLayout.error = "Слишком маленький номер"
                }
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        parentFragment?.view?.enableView()
        super.onDismiss(dialog)
    }
}