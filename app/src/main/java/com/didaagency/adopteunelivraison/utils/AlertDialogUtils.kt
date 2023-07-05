package com.didaagency.adopteunelivraison.utils

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager.BadTokenException
import androidx.appcompat.app.AlertDialog
import com.didaagency.adopteunelivraison.R
import java.lang.Exception
import javax.inject.Inject

/**
 * Created by Asad Fareed on 9/11/2022.
 */
class AlertDialogUtils @Inject constructor() {

    private var alertDialog: AlertDialog? = null

    fun showInternetDialog(context: Context, message: String?) {
        val builder = AlertDialog.Builder(context)
        builder.setCancelable(false)
        builder.setTitle("Alert")
        builder.setMessage(message)
        alertDialog = builder.create()
        alertDialog!!.setButton(
            DialogInterface.BUTTON_POSITIVE, "Close"
        ) { dialogInterface: DialogInterface?, i: Int ->
            alertDialog!!.hide()
            (context as Activity).finish()
        }
        alertDialog!!.show()
    }

    fun showLoadingDialog(context: Context?) {
        if (alertDialog != null) {
            if (alertDialog!!.isShowing) {
                alertDialog!!.dismiss()
            }
        }
        val view: View = LayoutInflater.from(context).inflate(R.layout.loading_dialog_view, null)
        val builder = AlertDialog.Builder(
            context!!
        )
        builder.setView(view)
        builder.setCancelable(false)
        alertDialog = builder.create()
        alertDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog!!.show()
    }

    fun showDialogYesNo(
        context: Context,
        title: String?,
        message: String?,
        onClickPositiveButton: DialogInterface.OnClickListener?,
        onClickNegativeButton: DialogInterface.OnClickListener?,
        bIsCancelable: Boolean
    ) {
        try {
            val sd = AlertDialog.Builder(context).create()
            sd.setCancelable(bIsCancelable)
            sd.setTitle(title)
            sd.setMessage(message)
            sd.setCanceledOnTouchOutside(bIsCancelable)
            sd.setButton(
                AlertDialog.BUTTON_POSITIVE,
                context.resources.getString(R.string.text_yes),
                onClickPositiveButton
            )
            sd.setButton(
                AlertDialog.BUTTON_NEGATIVE,
                context.resources.getString(R.string.text_no),
                onClickNegativeButton
            )
            sd.show()
        } catch (e: BadTokenException) {
            e.printStackTrace()
        }
    }

    fun showPermissionDialog(
        context: Context,
        title: String?,
        message: String?,
        onClickPositiveButton: DialogInterface.OnClickListener?,
        onClickNegativeButton: DialogInterface.OnClickListener?,
        bIsCancelable: Boolean
    ) {
        val builder = AlertDialog.Builder(context)
        builder.setCancelable(bIsCancelable)
        builder.setTitle(title)
        builder.setMessage(message)
        alertDialog = builder.create()
        alertDialog?.setButton(
            AlertDialog.BUTTON_POSITIVE,
            context.resources.getString(R.string.text_yes),
            onClickPositiveButton
        )
        alertDialog?.setButton(
            AlertDialog.BUTTON_NEGATIVE,
            context.resources.getString(R.string.text_no),
            onClickNegativeButton
        )
        alertDialog!!.show()
    }



    fun hideAlertDialog() {
        if (alertDialog != null) {
            if (alertDialog!!.isShowing) {
                alertDialog!!.dismiss()
            }
        }
    }

    fun showBlockerDialog(
        context: Context,
        title: String?,
        message: String?,
        bIsCancelable: Boolean
    ) {

        var alertDialog: androidx.appcompat.app.AlertDialog? = null
        val builder = androidx.appcompat.app.AlertDialog.Builder(context)
        builder.setCancelable(bIsCancelable)
        builder.setTitle(title)
        builder.setMessage(message)
        alertDialog = builder.create()
        alertDialog.setButton(
            androidx.appcompat.app.AlertDialog.BUTTON_NEGATIVE,
            context.resources.getString(R.string.text_ok)
        ) { dialogInterface: DialogInterface?, i: Int ->
            if (alertDialog != null) {
                alertDialog!!.hide()
            }
            (context as Activity).finish()
        }
        alertDialog!!.show()
    }


}