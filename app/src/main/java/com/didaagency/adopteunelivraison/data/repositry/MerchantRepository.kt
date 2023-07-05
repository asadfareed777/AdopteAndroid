package com.didaagency.adopteunelivraison.data.repositry

import com.didaagency.adopteunelivraison.data.database.AppDatabase
import com.didaagency.adopteunelivraison.data.network.ApiService
import com.didaagency.adopteunelivraison.data.network.request.LoginRequest
import com.didaagency.adopteunelivraison.data.network.request.UpdateMerchantRequest
import com.didaagency.adopteunelivraison.utils.AppUtils
import javax.inject.Inject

class MerchantRepository  @Inject constructor(
    private val apiHelper: ApiService,
    private val appUtils: AppUtils
) {
    suspend fun merchantsProfile() = apiHelper.merchantsProfile(appUtils.getDefaultHeaders(true))

    suspend fun merchantsProfile(id:String) = apiHelper.merchantsProfileById(appUtils.getDefaultHeaders(true),id)

    suspend fun updateMerchantsProfile(id:String,data:UpdateMerchantRequest) = apiHelper.updateMerchantsProfile(appUtils.getDefaultHeaders(true),id,data)

}