package com.didaagency.adopteunelivraison.view.login

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import com.didaagency.adopteunelivraison.R
import com.didaagency.adopteunelivraison.data.network.request.LoginRequest
import com.didaagency.adopteunelivraison.databinding.ActivityLoginBinding
import com.didaagency.adopteunelivraison.utils.*
import com.didaagency.adopteunelivraison.view.baseViews.BaseActivity
import com.didaagency.adopteunelivraison.view.dashboard.MainActivity
import com.didaagency.adopteunelivraison.view.forgotpassword.ForgotPasswordActivity
import com.didaagency.adopteunelivraison.view.signUp.SignUpActivity
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : BaseActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel:LoginViewModel by viewModels()
    private val PermissionsRequestCode = 123
    private lateinit var managePermissions: ManagePermissions
        @Inject set

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        setUpObservers()
        checkIfLogin()
        setUpPermissions()

        binding.etUsername.setText("dida")
        binding.etPassword.setText("dida123")
    }

    private fun setUpPermissions() {
        val list = listOf<String>(
//            Manifest.permission.CAMERA,
//            Manifest.permission.READ_EXTERNAL_STORAGE,
//            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        )

        // Initialize a new instance of ManagePermissions class
        managePermissions = ManagePermissions(this,list,PermissionsRequestCode)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            managePermissions.checkPermissions()
        }
    }

    private fun checkIfLogin() {
        if (AppPreference.getSavedData(this@LoginActivity, Constants.KEY_IS_LOGGED_IN)){
            IntentUtils.callIntentWithFlags(this,MainActivity::class.java,true)
        }
    }

    private fun initViews() {
//        binding.etCNIC.addTextChangedListener(TextWatcherCnic(binding.etCNIC))

        binding.model = loginViewModel
    }

    private fun setUpObservers() {
        lifecycleScope.launchWhenStarted {
            loginViewModel.userResponse.observe(this@LoginActivity) {
                when (it.status) {
                    Status.SUCCESS -> {
                        //AppUtils.hideCustomProgress()
                        showToast(it.message.toString())
                        AppPreference.saveValue(this@LoginActivity, it.data?.accessToken,
                            Constants.KEY_ACCESS_TOKEN)
                        val user: String = Gson().toJson(it.data?.user)
                        AppPreference.saveValue(this@LoginActivity, user, Constants.KEY_USER)
                        AppPreference.saveValue(this@LoginActivity, binding.etPassword.text.toString().trim(), Constants.KEY_USER_PASSOWRD)
                        AppPreference.saveUser(this@LoginActivity, user)
                        AppPreference.saveValue(this@LoginActivity,
                            it.data?.user?.merchantId!!.toString(), Constants.KEY_USER_ID)
                        AppPreference.saveData(this@LoginActivity, true, Constants.KEY_IS_LOGGED_IN)
                        checkIfLogin()
                    }
                    Status.ERROR -> {
                        hideLoadingDialog()
                        //AppUtils.hideCustomProgress()
                        showToast(it.message.toString())
                    }
                    Status.LOADING -> {
                        showLoadingDialog()
                    }
                }
            }

            loginViewModel.clickEvent.observe(this@LoginActivity) {
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
                    ClickEvents.LOGIN -> {
                        loginClick()
                    }
                    ClickEvents.SIGNUP -> {
                        IntentUtils.callIntentWithFlags(this@LoginActivity,SignUpActivity::class.java,false)
                    }
                    ClickEvents.FORGET_PASSWORD_CLICK -> {
                        IntentUtils.callIntentWithFlags(this@LoginActivity,ForgotPasswordActivity::class.java,false)
                    }
                }
            }
        }


    }


    fun loginClick(){
        try {
            AppUtils.hideKeyboard(this)
            if (isValid()){
//                showToast("Api Under Process")
//                IntentUtils.callIntentWithFlags(this,MainActivity::class.java,true)
                callLoginApi(
                    binding.etUsername.text.toString().trim { it <= ' ' }
                        .replace("-".toRegex(), ""),
                    binding.etPassword.text.toString().trim() /*{ it <= ' ' }
                                .replace("-".toRegex(), "")*/)


            }
        }catch (exception:Exception){
            exception.printStackTrace()
        }
    }
    private fun callLoginApi(username: String, password: String) {
        val loginRequest = LoginRequest(
            username = username,
            password = password,
            login_type = "merchants"
        )
        loginViewModel.login(loginRequest)
    }

    private fun isValid(): Boolean {
        if (binding.etUsername.text.toString().trim { it <= ' ' } == "") {
            binding.etUsername.error = "Please enter username"
            binding.etUsername.requestFocus()
            return false
        }

        if (binding.etPassword.text.toString().trim { it <= ' ' } == "") {
            binding.etPassword.error = "Please enter password"
            binding.etPassword.requestFocus()
            return false
        }
        if (!NetworkUtility.isNetworkAvailable(this@LoginActivity)) {
            loginViewModel.internetConnectionError()
            return false
        }
        return true
    }
}
