package com.didaagency.adopteunelivraison.view.fragments.accounts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.didaagency.adopteunelivraison.R
import com.didaagency.adopteunelivraison.databinding.ActivityChangePasswordBinding
import com.didaagency.adopteunelivraison.databinding.ActivityEditProfileBinding
import com.didaagency.adopteunelivraison.utils.AppPreference
import com.didaagency.adopteunelivraison.utils.AppUtils
import com.didaagency.adopteunelivraison.utils.ClickEvents
import com.didaagency.adopteunelivraison.utils.Constants
import com.didaagency.adopteunelivraison.view.baseViews.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangePasswordActivity : BaseActivity() {

    private lateinit var binding: ActivityChangePasswordBinding
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()

    }

    private fun initViews() {
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.model = viewModel
        setUpObservers()
    }

    private fun setUpObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.clickEvent.observe(this@ChangePasswordActivity) {
                when (it) {
                    ClickEvents.BACK -> {
                        finish()
                    }
                    ClickEvents.INTERNET_CONNECTION -> {
                        showToast(resources.getString(R.string.internet_connection_message))
                    }
                    ClickEvents.UPDATE -> {
                        updatePassowrdClick()
                    }
                    ClickEvents.EYE_PASSWORD_CLICK -> {
                        AppUtils.hideShowPassword(binding.etOldPassword, binding.ivEye)
                    }
                    ClickEvents.NEW_EYE_PASSWORD_CLICK -> {
                        AppUtils.hideShowPassword(binding.etNewPassword, binding.ivEyeNew)
                    }
                    ClickEvents.CONFIRM_EYE_PASSWORD_CLICK -> {
                        AppUtils.hideShowPassword(binding.etConfirmPassword, binding.ivEyeConfirm)
                    }
                }
            }
        }
    }


    private fun updatePassowrdClick() {
        if (isValidate()) {
            showToast("Api Under Process")
        }
    }


    private fun isValidate(): Boolean {
        if (binding.etOldPassword.text.toString().trim().isEmpty()) {
            binding.etOldPassword.error = resources.getString(R.string.text_old_password_error)
            return false
        }
        if (!binding.etOldPassword.text.toString().trim().equals(AppPreference.getValue(this,Constants.KEY_USER_PASSOWRD))) {
            binding.etOldPassword.error = resources.getString(R.string.text_old_password_error)
            return false
        }
        if (binding.etNewPassword.text.toString().trim().isEmpty()) {
            binding.etNewPassword.error = resources.getString(R.string.text_new_password_error)
            return false
        }
        if (binding.etConfirmPassword.text.toString().trim().isEmpty()) {
            binding.etConfirmPassword.error = resources.getString(R.string.text_confirm_password_error)
            return false
        }
        if (!binding.etNewPassword.text.toString().trim().equals(binding.etConfirmPassword.text.toString().trim())) {
            binding.etConfirmPassword.error = resources.getString(R.string.text_password_not_match_error)
            return false
        }

        return true
    }

}