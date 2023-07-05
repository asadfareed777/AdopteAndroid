package com.didaagency.adopteunelivraison.view.baseViews

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.didaagency.adopteunelivraison.data.network.response.User
import com.didaagency.adopteunelivraison.utils.AlertDialogUtils
import com.didaagency.adopteunelivraison.utils.AppPreference
import com.didaagency.adopteunelivraison.utils.Constants
import javax.inject.Inject

open class BaseFragment :Fragment() {

    @Inject
    protected lateinit var alertDialogUtils: AlertDialogUtils


    fun showToast(msg: String) {
        Toast.makeText(
            requireActivity(), msg, Toast.LENGTH_SHORT
        ).show()
    }

    fun hideLoadingDialog(){
        alertDialogUtils!!.hideAlertDialog()
    }
    fun showLoadingDialog(){
        alertDialogUtils!!.showLoadingDialog(requireContext())
    }

    protected fun getLoggedInUser(): User?{
        if (AppPreference.getSavedData(requireContext(), Constants.KEY_IS_LOGGED_IN)){
            return AppPreference.getUser(requireContext())
        }
        return null
    }


}