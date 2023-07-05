package com.didaagency.adopteunelivraison.base

import android.app.Application
import com.didaagency.adopteunelivraison.R
import dagger.hilt.android.HiltAndroidApp
import com.didaagency.adopteunelivraison.utils.Constants
import com.onesignal.OneSignal

@HiltAndroidApp
class BaseApp : Application(){

    companion object {
        init {
            System.loadLibrary("native-lib")
        }

        lateinit var instance: BaseApp
            private set
    }

    override fun onCreate() {
        super.onCreate()
        Constants.BASE_URL = baseurl()
        Constants.BASE_URL_API = apiurl()

        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.DEBUG, OneSignal.LOG_LEVEL.NONE)
        OneSignal.initWithContext(this)
        OneSignal.setAppId(resources.getString(R.string.onesignal_app_id))

    }

    external fun baseurl(): String

    external fun apiurl(): String
}