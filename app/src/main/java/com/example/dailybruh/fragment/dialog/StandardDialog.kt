package com.example.dailybruh.fragment.dialog

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import com.example.dailybruh.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

open class StandardDialog<T : ViewBinding> : BottomSheetDialogFragment() {

    var _binding: T? = null
    val binding: T get() = requireNotNull(_binding)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle)
    }

}