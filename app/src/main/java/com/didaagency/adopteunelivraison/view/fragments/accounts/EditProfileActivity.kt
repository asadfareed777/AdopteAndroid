package com.didaagency.adopteunelivraison.view.fragments.accounts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.didaagency.adopteunelivraison.R
import com.didaagency.adopteunelivraison.databinding.ActivityEditProfileBinding
import com.didaagency.adopteunelivraison.databinding.ActivityForgotPasswordBinding
import com.didaagency.adopteunelivraison.databinding.ActivityProfileBinding
import com.didaagency.adopteunelivraison.utils.AppUtils
import com.didaagency.adopteunelivraison.utils.ClickEvents
import com.didaagency.adopteunelivraison.utils.Status
import com.didaagency.adopteunelivraison.view.baseViews.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileActivity : BaseActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    private val viewModel: ProfileViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        setData()
    }

    private fun setData() {
        binding.etStoreName.setText(getLoggedInUser()?.contactName)
        binding.etStoreAddress.setText(getLoggedInUser()?.address)
        binding.etEmail.setText(getLoggedInUser()?.contactEmail)
        binding.etPhoneNumber.setText(getLoggedInUser()?.contactPhone)
    }

    private fun initViews() {
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.model = viewModel
        setUpObservers()
    }

    private fun setUpObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.clickEvent.observe(this@EditProfileActivity) {
                when (it) {
                    ClickEvents.BACK -> {
                        finish()
                    }
                    ClickEvents.INTERNET_CONNECTION -> {
                        showToast(resources.getString(R.string.internet_connection_message))
                    }
                    ClickEvents.UPDATE -> {
                        updateProfileClick()
                    }
                    ClickEvents.CAMERA_REQUEST -> {
                        cameraRequest()
                    }
                }
            }
        }
    }

    private fun cameraRequest() {
        showToast("Camera Request Click")
    }

    private fun updateProfileClick() {
        if (isValidate()){
            showToast("Api Under Process")
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
        return true
    }

}