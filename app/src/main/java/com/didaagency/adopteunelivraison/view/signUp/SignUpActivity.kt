package com.didaagency.adopteunelivraison.view.signUp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.didaagency.adopteunelivraison.R
import com.didaagency.adopteunelivraison.data.network.request.RegisterRequest
import com.didaagency.adopteunelivraison.databinding.ActivityForgotPasswordBinding
import com.didaagency.adopteunelivraison.databinding.ActivitySignUpBinding
import com.didaagency.adopteunelivraison.utils.*
import com.didaagency.adopteunelivraison.view.baseViews.BaseActivity
import com.didaagency.adopteunelivraison.view.forgotpassword.ForgotPasswordViewModel
import com.didaagency.adopteunelivraison.view.login.LoginActivity
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : BaseActivity() {


    private lateinit var binding: ActivitySignUpBinding
    private val viewModel: SignUpViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        setUpObservers()
    }

    private fun initViews() {
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.model = viewModel
    }

    private fun setUpObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.userResponse.observe(this@SignUpActivity) {
                when (it.status) {
                    Status.SUCCESS -> {
                        hideLoadingDialog()
                        showToast(it.message.toString())
                        IntentUtils.callIntentWithFlags(this@SignUpActivity,LoginActivity::class.java,true)
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

            viewModel.clickEvent.observe(this@SignUpActivity) {
                when (it) {
                    ClickEvents.BACK -> {
                        finish()
                    }
                    ClickEvents.INTERNET_CONNECTION -> {
                        showToast(resources.getString(R.string.internet_connection_message))
                    }
                    ClickEvents.EYE_PASSWORD_CLICK -> {
                        AppUtils.hideShowPassword(binding.etPassword, binding.ivEye)
                    }
                    ClickEvents.SIGNUP -> {
                        callSignUpClick()
                    }
                }
            }
        }
    }

    private fun callSignUpClick() {
        if (isValidate()) {
            var merchantType = ""
            if (binding.rbtnMembership.isChecked){
                merchantType  = "1"
            }else if (binding.rbtnCommission.isChecked){
                merchantType  = "2"
            }
            var request = RegisterRequest(
                name = binding.etStoreName.text.toString().trim(),
                email = binding.etEmail.text.toString().trim(),
                contactPhone = binding.etPhoneNumber.number.toString().trim(),
                address = binding.etStoreAddress.text.toString().trim(),
                password = binding.etPassword.text.toString().trim(),
                merchantType = merchantType
            )
            viewModel.register(request)
        }
    }

    private fun isValidate(): Boolean {
        if (binding.etStoreName.text.toString().trim().isEmpty()) {
            binding.etStoreName.error = resources.getString(R.string.text_store_name_error)
            return false
        }
        if (binding.etStoreAddress.text.toString().trim().isEmpty()) {
            binding.etStoreAddress.error = resources.getString(R.string.text_store_address_error)
            return false
        }
        if (!AppUtils.isEmailValid(binding.etEmail.text.toString().trim())) {
            binding.etEmail.error = resources.getString(R.string.text_email_error)
            return false
        }
        if (!binding.etPhoneNumber.isValidNumber) {
            binding.etPhoneNumber.setError(resources.getString(R.string.text_phone_number_error))
            return false
        }
        if (binding.etPassword.text.toString().trim().isEmpty()) {
            binding.etPassword.setError(resources.getString(R.string.text_password_error))
            return false
        }
        if (!binding.rbtnCommission.isChecked && !binding.rbtnMembership.isChecked) {
            showToast(resources.getString(R.string.text_membership_program_error))
            return false
        }
        if (!binding.cbPrivacy.isChecked) {
            showToast(resources.getString(R.string.text_terms_condition_error))
            return false
        }
        return true
    }
}