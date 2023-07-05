package com.didaagency.adopteunelivraison.data.network.request.orders

import com.google.gson.annotations.SerializedName

data class Customer(

    @field:SerializedName("name")
    var name: String? = null,

    @field:SerializedName("number_of_order_complete")
    var numberOfOrderComplete: String? = null,

    @field:SerializedName("image")
    var image: String? = null,

    @field:SerializedName("date")
    var date: String? = null,

    @field:SerializedName("customer_id")
    var customerId: Int? = null
)
