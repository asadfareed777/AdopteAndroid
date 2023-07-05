package com.didaagency.adopteunelivraison.data.repositry

import com.didaagency.adopteunelivraison.data.database.AppDatabase
import com.didaagency.adopteunelivraison.data.network.ApiService
import com.didaagency.adopteunelivraison.data.network.request.UpdateMerchantRequest
import com.didaagency.adopteunelivraison.data.network.request.UpdateOrderRequest
import com.didaagency.adopteunelivraison.utils.AppUtils
import javax.inject.Inject

class OrdersRepository @Inject constructor(
    private val apiHelper: ApiService,
    private val appUtils: AppUtils
) {

    suspend fun getOrders() = apiHelper.getOrders(appUtils.getDefaultHeaders(true))

    suspend fun getOrderStats() = apiHelper.getOrderStats(appUtils.getDefaultHeaders(true))

    suspend fun getOrderById(id:String) = apiHelper.getOrderById(appUtils.getDefaultHeaders(true),id)

    suspend fun updateOrder(id:String,data: UpdateOrderRequest) = apiHelper.updateOrder(appUtils.getDefaultHeaders(true),id,data)

}