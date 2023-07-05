package com.didaagency.adopteunelivraison.base

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import com.didaagency.adopteunelivraison.utils.Constants
import javax.inject.Inject

@HiltAndroidApp
class BaseApp : Application(){

    companion object {

        lateinit var instance: BaseApp
            private set
    }
    override fun onCreate() {
        super.onCreate()
        Constants.BASE_URL = "http://dev.bapps.pitb.gov.pk"
        Constants.BASE_URL_API = "http://dev.bapps.pitb.gov.pk/saaf_punjab_beta/api_pose/"

        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.DEBUG, OneSignal.LOG_LEVEL.NONE)
        OneSignal.initWithContext(this)
        OneSignal.setAppId(resources.getString(R.string.onesignal_app_id))
    }
}