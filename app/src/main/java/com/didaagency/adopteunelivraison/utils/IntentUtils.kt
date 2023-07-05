package com.didaagency.adopteunelivraison.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import javax.inject.Inject

class IntentUtils  @Inject constructor(){
    companion object {

        fun callIntentWithFlags(c: Context, cls: Class<*>?, flags: Boolean) {
            val intent = Intent(c, cls)
            if (flags) {
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.flags =
                    Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            c.startActivity(intent)
        }

        fun callIntentWithFlagsBundle(c: Context, cls: Class<*>?, flags: Boolean, bundle: Bundle?) {
            val intent = Intent(c, cls)
            if (flags) {
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.flags =
                    Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            }
            intent.putExtras(bundle!!)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            c.startActivity(intent)
        }

        fun callIntentWithFlagsBundleForResult(
            c: Context,
            cls: Class<*>?,
            flags: Boolean,
            bundle: Bundle?,
            requestCode: Int
        ) {
            val intent = Intent(c, cls)
            if (flags) {
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.flags =
                    Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            }
            intent.putExtras(bundle!!)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            (c as Activity).startActivityForResult(intent, requestCode)
        }

    }
}