package com.example.dailybruh.fragment.dialog

import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

open class StandardDialog<T : ViewBinding> : BottomSheetDialogFragment() {

    var _binding: T? = null
    val binding: T = requireNotNull(_binding)
}