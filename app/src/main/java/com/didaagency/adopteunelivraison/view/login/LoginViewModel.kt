package com.didaagency.adopteunelivraison.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import com.didaagency.adopteunelivraison.data.network.request.LoginRequest
import com.didaagency.adopteunelivraison.data.network.response.User
import com.didaagency.adopteunelivraison.data.network.response.login.LoginData
import com.didaagency.adopteunelivraison.data.repositry.AuthRepository
import com.didaagency.adopteunelivraison.utils.ClickEvents
import com.didaagency.adopteunelivraison.utils.Constants
import com.didaagency.adopteunelivraison.utils.Resource
import com.didaagency.adopteunelivraison.view.baseViews.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel() {

    private val _userResponse = MutableLiveData<Resource<LoginData>>()

    val userResponse: LiveData<Resource<LoginData>>
        get() = _userResponse

    init {
        //login()
    }

    fun login(data: LoginRequest) = viewModelScope.launch {
        try {
            _userResponse.postValue(Resource.loading(null))
            authRepository.login(data).let {
                if (it.statusCode==Constants.SUCCESS_CODE) {
                    _userResponse.postValue(
                        Resource.success(
                            data = it.data!!,
                            message = it.message!!
                        )
                    )
                } else {
                    _userResponse.postValue(Resource.error(data = null, message = it.message!!))
                }
            }
        } catch (exception: Exception) {
            //exception.printStackTrace()
            _userResponse.postValue(
                Resource.error(
                    data = null,
                    message = exception.message ?: "Something went wrong"
                )
            )
        }
    }

}