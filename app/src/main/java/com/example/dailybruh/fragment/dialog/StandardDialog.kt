package com.example.dailybruh.fragment.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import com.example.dailybruh.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

open class StandardDialog<T : ViewBinding> : BottomSheetDialogFragment() {

    var _binding: T? = null
    val binding: T get() = requireNotNull(_binding)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener {
            (it as BottomSheetDialog).behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
        return dialog
    }
}