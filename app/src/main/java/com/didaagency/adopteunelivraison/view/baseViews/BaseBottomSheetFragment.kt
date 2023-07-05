package com.didaagency.adopteunelivraison.view.baseViews

import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

open class BaseBottomSheetFragment : BottomSheetDialogFragment()  {

    protected fun showToast(msg: String) {
        Toast.makeText(
            requireContext(), msg, Toast.LENGTH_SHORT
        ).show()
    }

}