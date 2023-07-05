package com.didaagency.adopteunelivraison.view.forgotpassword

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.didaagency.adopteunelivraison.R
import com.didaagency.adopteunelivraison.data.network.request.ForgotRequest
import com.didaagency.adopteunelivraison.databinding.ActivityForgotPasswordBinding
import com.didaagency.adopteunelivraison.databinding.ActivityLoginBinding
import com.didaagency.adopteunelivraison.utils.*
import com.didaagency.adopteunelivraison.view.baseViews.BaseActivity
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPasswordActivity : BaseActivity() {

    private lateinit var binding: ActivityForgotPasswordBinding
    private val viewModel: ForgotPasswordViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        setUpObservers()
    }

    private fun initViews() {
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.model = viewModel
    }

    private fun setUpObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.forgotPasswordResponse.observe(this@ForgotPasswordActivity) {
                when (it.status) {
                    Status.SUCCESS -> {
                        hideLoadingDialog()
                        showToast(it.message.toString())
                        finish()
                    }
                    Status.ERROR -> {
                        hideLoadingDialog()
                        showToast(it.message.toString())
                    }
                    Status.LOADING -> {
                        showLoadingDialog()
                    }
                }
            }

            viewModel.clickEvent.observe(this@ForgotPasswordActivity) {
                when (it) {
                    ClickEvents.BACK -> {
                        finish()
                    }
                    ClickEvents.INTERNET_CONNECTION -> {
                        showToast(resources.getString(R.string.internet_connection_message))
                    }
                    ClickEvents.RESET_PASSWORD -> {
                        callResetPasswordClick()
                    }
                }
            }
        }


    }

    private fun callResetPasswordClick() {
        if (isValidate()){
            var request = ForgotRequest(
                email = binding.etEmail.text.toString().trim()
            )
            viewModel.forgotPassword(request)
        }
    }

    private fun isValidate(): Boolean {
        if(!AppUtils.isEmailValid(binding.etEmail.text.toString().trim())){
            binding.etEmail.error = resources.getString(R.string.text_email_error)
            return false
        }
        return true
    }
}