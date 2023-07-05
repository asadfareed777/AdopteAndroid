package com.didaagency.adopteunelivraison.view.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import com.didaagency.adopteunelivraison.data.network.response.*
import com.didaagency.adopteunelivraison.data.repositry.AuthRepository
import com.didaagency.adopteunelivraison.utils.Resource
import com.didaagency.adopteunelivraison.view.baseViews.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val AuthRepository: AuthRepository
): BaseViewModel(){

    private var insertedDataEmbedding:Boolean = false
    private val _syncContainerResponse = MutableLiveData<Resource<ArrayList<ContainerData>>>()

    val syncContainerResponse : LiveData<Resource<ArrayList<ContainerData>>>
        get() = _syncContainerResponse

    init {
    }

    fun syncUcContainers(imei:String,uc_id:String,district_id:String,app_version:String)  = viewModelScope.launch {
        try {
            _syncContainerResponse.postValue(Resource.loading(null))
            AuthRepository.syncUcContainers(imei, uc_id, district_id, app_version).let {
                if (it.status!!){
                    _syncContainerResponse.postValue(Resource.success(data = it.containerData,message = it.message!!))
                    AuthRepository.saveContainers(it.containerData)
                }else{
                    _syncContainerResponse.postValue(Resource.error(data = null, message = it.message!!))
                }
            }
        }catch (exception:Exception){
           // exception.printStackTrace()
            _syncContainerResponse.postValue(Resource.error(data = null, message = exception.message?:"Something went wrong"))
        }
    }


    fun clearAllTableData() {
        viewModelScope.launch {
            try {
                AuthRepository.clearAllTableData()
            }catch (exception:Exception){
                exception.printStackTrace()
            }
        }
    }

}