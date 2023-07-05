package com.didaagency.adopteunelivraison.data.network

import com.didaagency.adopteunelivraison.data.network.request.*
import com.google.gson.JsonArray
import com.google.gson.annotations.SerializedName
import org.json.JSONArray
import com.didaagency.adopteunelivraison.data.network.response.*
import com.didaagency.adopteunelivraison.data.network.response.login.LoginResponse
import com.didaagency.adopteunelivraison.data.network.response.orders.OrderListResponse
import com.didaagency.adopteunelivraison.data.network.response.orders.OrderStatsResponse
import com.didaagency.adopteunelivraison.utils.Constants
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @POST(Constants.API_LOGIN)
    suspend fun login(
        @HeaderMap header: Map<String, String>?,
        @Body request: LoginRequest
    ): LoginResponse

    @POST(Constants.API_REGISTER)
    suspend fun register(
        @HeaderMap header: Map<String, String>?,
        @Body request: RegisterRequest
    ): LoginResponse

    @POST(Constants.API_REGISTER)
    suspend fun forgotPassword(
        @HeaderMap header: Map<String, String>?,
        @Body request: ForgotRequest
    ): ServerResponse

//   *************  Merchant Apis   **************

    @GET(Constants.API_MERCHANT_PROFILE)
    suspend fun merchantsProfile(
        @HeaderMap header: Map<String, String>?
    ): MerchantProfileResponse

    @GET(Constants.API_MERCHANT_PROFILE+"/{id}")
    suspend fun merchantsProfileById(
        @HeaderMap header: Map<String, String>?,
        @Path("id") id : String
    ): MerchantProfileResponse


    @POST(Constants.API_MERCHANT_PROFILE+"/{id}")
    suspend fun updateMerchantsProfile(
        @HeaderMap header: Map<String, String>?,
        @Path("id") id : String,
        @Body request: UpdateMerchantRequest

    ): ServerResponse


//   *************  Orders Apis   **************

    @GET(Constants.API_MERCHANT_ORDERS)
    suspend fun getOrders(
        @HeaderMap header: Map<String, String>?
    ): OrderListResponse

    @GET(Constants.API_MERCHANT_ORDERS+"/{id}")
    suspend fun getOrderById(
        @HeaderMap header: Map<String, String>?,
        @Path("id") id : String,
    ): OrderListResponse

    @GET(Constants.API_MERCHANT_ORDER_STATS)
    suspend fun getOrderStats(
        @HeaderMap header: Map<String, String>?
    ): OrderStatsResponse


    @POST(Constants.API_MERCHANT_ORDERS+"/{id}")
    suspend fun updateOrder(
        @HeaderMap header: Map<String, String>?,
        @Path("id") id : String,
        @Body request: UpdateOrderRequest

    ): ServerResponse


    @GET("ucContainers")
    suspend fun syncUcContainers(@Query("uc_id") ucId: String?,@Query("Imei") imei: String?,
                                 @Query("app_version") app_version: String?,
                                 @Query("district_id") districtId: String?): SyncContainersResponse

}