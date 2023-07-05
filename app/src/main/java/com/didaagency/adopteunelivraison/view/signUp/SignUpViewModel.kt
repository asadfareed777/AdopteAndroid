package com.didaagency.adopteunelivraison.view.signUp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.didaagency.adopteunelivraison.data.network.request.LoginRequest
import com.didaagency.adopteunelivraison.data.network.request.RegisterRequest
import com.didaagency.adopteunelivraison.data.network.response.login.LoginData
import com.didaagency.adopteunelivraison.data.repositry.AuthRepository
import com.didaagency.adopteunelivraison.utils.Constants
import com.didaagency.adopteunelivraison.utils.Resource
import com.didaagency.adopteunelivraison.view.baseViews.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel()  {

    private val _userResponse = MutableLiveData<Resource<LoginData>>()
    val userResponse: LiveData<Resource<LoginData>>
        get() = _userResponse

    fun register(data: RegisterRequest) = viewModelScope.launch {
        try {
            _userResponse.postValue(Resource.loading(null))
            authRepository.register(data).let {
                if (it.statusCode== Constants.SUCCESS_CODE) {
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