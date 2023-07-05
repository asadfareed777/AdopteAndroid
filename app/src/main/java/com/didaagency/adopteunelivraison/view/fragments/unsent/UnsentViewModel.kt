package com.didaagency.adopteunelivraison.view.fragments.unsent

import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.didaagency.adopteunelivraison.data.database.entities.UnsentData
import com.didaagency.adopteunelivraison.data.network.request.*
import com.didaagency.adopteunelivraison.data.network.response.*
import com.didaagency.adopteunelivraison.data.repositry.AuthRepository
import com.didaagency.adopteunelivraison.utils.Resource
import com.didaagency.adopteunelivraison.view.dashboard.MainViewModel
import javax.inject.Inject

@HiltViewModel
class UnsentViewModel @Inject constructor(
    private val AuthRepository: AuthRepository
): ViewModel(){

    private val _containerUnsentList = MutableLiveData<Resource<ArrayList<UnsentData>>>()
    private val _updateContainerResponse = MutableLiveData<Resource<SaveContainerRequest>>()

    val containerUnsentList : LiveData<Resource<ArrayList<UnsentData>>>
        get() = _containerUnsentList
    val updateContainerResponse : LiveData<Resource<SaveContainerRequest>>
        get() = _updateContainerResponse


    fun saveUnsentContainer(unsentData: UnsentData)  = viewModelScope.launch {
        try {
            AuthRepository.saveUnsentData(unsentData)
        }catch (exception:Exception){
            exception.printStackTrace()
        }
    }

    fun clearSentData(json:String){
        viewModelScope.launch {
            try {
                AuthRepository.clearSentData(json)
            }catch (exception:Exception){
                exception.printStackTrace()
            }
        }
    }

    fun getUnsentList() = viewModelScope.launch {
        try {
            _containerUnsentList.postValue(Resource.loading(null))
            delay(1000)
            _containerUnsentList.postValue(Resource.success(data = AuthRepository.getUnsentList() as ArrayList,
                message = "Data Fetched successfully"))
          //  _containerUnsentList.value = AuthRepository.getUnsentList() as ArrayList
           // AuthRepository.getUnsentList()
        }catch (exception:Exception){
            _containerUnsentList.postValue(Resource.error(data = null, message = exception.message?:"Something went wrong"))
           // exception.printStackTrace()
        }
    }

}