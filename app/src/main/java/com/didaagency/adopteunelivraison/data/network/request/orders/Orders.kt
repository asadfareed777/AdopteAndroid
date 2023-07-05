package com.didaagency.adopteunelivraison.data.network.request.orders

import com.google.gson.annotations.SerializedName

data class Orders(

    @field:SerializedName("date")
    var date: String? = null,

    @field:SerializedName("order_status")
    var orderStatus: String? = null,

    @field:SerializedName("payment_status")
    var paymentStatus: String? = null,

    @field:SerializedName("delivery_status")
    var deliveryStatus: String? = null,

    @field:SerializedName("description")
    var description: String? = null,

    @field:SerializedName("title")
    var title: String? = null,

    @field:SerializedName("price")
    var price: String? = null,

    @field:SerializedName("service_fee")
    var serviceFee: String? = null,

    @field:SerializedName("image")
    var image: String? = null,

    @field:SerializedName("order_id")
    var orderId: Int? = null
)
