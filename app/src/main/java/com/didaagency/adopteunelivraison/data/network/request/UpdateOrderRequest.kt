package com.didaagency.adopteunelivraison.data.network.request

import com.google.gson.annotations.SerializedName

data class UpdateOrderRequest(

    @field:SerializedName("_method")
    var method: String,
    @field:SerializedName("status")
    var status: String,
    @field:SerializedName("payment_status")
    var paymentStatus: String,
    @field:SerializedName("delivery_status")
    var deliveryStatus: String,
    @field:SerializedName("service_code")
    var serviceCode: String,
    @field:SerializedName("payment_code")
    var paymentCode: String,
    @field:SerializedName("total_discount")
    var totalDiscount: String
)