package com.didaagency.adopteunelivraison.view.baseViews

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import com.google.android.material.snackbar.Snackbar
import com.didaagency.adopteunelivraison.R
import com.didaagency.adopteunelivraison.data.network.response.User
import com.didaagency.adopteunelivraison.utils.*
import javax.inject.Inject

open class BaseActivity : AppCompatActivity() {

    @Inject
    protected lateinit var alertDialogUtils: AlertDialogUtils

    @Inject
    protected lateinit  var intentUtils : IntentUtils

    @Inject
    protected lateinit  var appUtils : AppUtils

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

    protected fun showToast(msg: String) {
        Toast.makeText(
            this, msg, Toast.LENGTH_SHORT
        ).show()
    }

    protected fun hideLoadingDialog(){
        alertDialogUtils!!.hideAlertDialog()
    }
    protected fun showLoadingDialog(){
        alertDialogUtils!!.showLoadingDialog(this)
    }



    protected fun showBlockerDialog(context : Context, title: String?, message: String?){
        alertDialogUtils.showBlockerDialog(
            context,
            title,message,
            false
        )
    }
    protected fun showBlockerDialog( context : Context, title: String?,
                                      message: String?,
                                      onClickPositiveButton: DialogInterface.OnClickListener?,
                                      onClickNegativeButton: DialogInterface.OnClickListener?,
                                      bIsCancelable: Boolean){
        alertDialogUtils.showPermissionDialog(
            context,
            title,message,
            onClickPositiveButton,
            onClickNegativeButton,
            bIsCancelable
        )
    }

    protected fun getLoggedInUser(): User?{
        if (AppPreference.getSavedData(this,Constants.KEY_IS_LOGGED_IN)){
            return AppPreference.getUser(this)
        }
        return null
    }


}