package com.didaagency.adopteunelivraison.view.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatDelegate
import com.didaagency.adopteunelivraison.databinding.ActivitySplashBinding
import com.didaagency.adopteunelivraison.utils.AppPreference
import com.didaagency.adopteunelivraison.utils.Constants
import com.didaagency.adopteunelivraison.utils.IntentUtils
import com.didaagency.adopteunelivraison.view.baseViews.BaseActivity
import com.didaagency.adopteunelivraison.view.dashboard.MainActivity
import com.didaagency.adopteunelivraison.view.forgotpassword.ForgotPasswordActivity
import com.didaagency.adopteunelivraison.view.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val SPLASH_DISPLAY_LENGTH: Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    override fun onResume() {
        super.onResume()
        setUpDelayHandler()
    }

    private fun initViews() {
//        binding.tvVersion.text = AppUtils.getAppVersion()
    }

    private fun setUpDelayHandler() {
        try {
            Handler(Looper.getMainLooper()).postDelayed({
                if (AppPreference.getSavedData(this, Constants.KEY_IS_LOGGED_IN)) {
                    IntentUtils.callIntentWithFlags(this, MainActivity::class.java, true)
                } else {
                    IntentUtils.callIntentWithFlags(this, LoginActivity::class.java, true)
                }
            }, SPLASH_DISPLAY_LENGTH)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}