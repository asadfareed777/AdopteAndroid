package com.didaagency.adopteunelivraison.view.fragments.home_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.didaagency.adopteunelivraison.data.network.response.orders.OrderStatsResponse
import com.didaagency.adopteunelivraison.data.repositry.AuthRepository
import com.didaagency.adopteunelivraison.data.repositry.MerchantRepository
import com.didaagency.adopteunelivraison.data.repositry.OrdersRepository
import com.didaagency.adopteunelivraison.utils.Resource
import com.didaagency.adopteunelivraison.view.baseViews.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MerchantRepository,
    private val orderRepository: OrdersRepository
) : BaseViewModel() {
    private val _orderStatsResponse = MutableLiveData<Resource<OrderStatsResponse>>()

    val orderStatsResponse: LiveData<Resource<OrderStatsResponse>>
        get() = _orderStatsResponse

    fun orderStats() = viewModelScope.launch {
        try {
            _orderStatsResponse.postValue(Resource.loading(null))
            orderRepository.getOrderStats().let {
                _orderStatsResponse.postValue(
                    Resource.success(
                        data = it!!,
                        message = "Success"
                    )
                )
            }
        } catch (exception: Exception) {
            //exception.printStackTrace()
            _orderStatsResponse.postValue(
                Resource.error(
                    data = null,
                    message = exception.message ?: "Something went wrong"
                )
            )
        }
    }

}