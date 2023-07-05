package com.didaagency.adopteunelivraison.view.forgotpassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.didaagency.adopteunelivraison.data.network.request.ForgotRequest
import com.didaagency.adopteunelivraison.data.network.request.RegisterRequest
import com.didaagency.adopteunelivraison.data.network.response.ServerResponse
import com.didaagency.adopteunelivraison.data.network.response.login.LoginData
import com.didaagency.adopteunelivraison.data.repositry.AuthRepository
import com.didaagency.adopteunelivraison.utils.Constants
import com.didaagency.adopteunelivraison.utils.Resource
import com.didaagency.adopteunelivraison.view.baseViews.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel()  {
    private val _forgotPasswordResponse = MutableLiveData<Resource<ServerResponse>>()
    val forgotPasswordResponse: LiveData<Resource<ServerResponse>>
        get() = _forgotPasswordResponse

    fun forgotPassword(data: ForgotRequest) = viewModelScope.launch {
        try {
            _forgotPasswordResponse.postValue(Resource.loading(null))
            authRepository.forgotPassword(data).let {
                if (it.statusCode== Constants.SUCCESS_CODE) {
                    _forgotPasswordResponse.postValue(
                        Resource.success(
                            data = it,
                            message = it.message
                        )
                    )
                } else {
                    _forgotPasswordResponse.postValue(Resource.error(data = null, message = it.message))
                }
            }
        } catch (exception: Exception) {
            //exception.printStackTrace()
            _forgotPasswordResponse.postValue(
                Resource.error(
                    data = null,
                    message = exception.message ?: "Something went wrong"
                )
            )
        }
    }
}