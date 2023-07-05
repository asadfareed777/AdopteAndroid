package com.didaagency.adopteunelivraison.data.network.response.orders

import com.google.gson.annotations.SerializedName

data class OrderListResponse(

    @field:SerializedName("status_code")
	val statusCode: Int? = null,

    @field:SerializedName("data")
	val data: List<String>? = null,

    @field:SerializedName("message")
	var message: String? = ""
)