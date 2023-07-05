package com.didaagency.adopteunelivraison.data.network.response

import com.google.gson.annotations.SerializedName

data class MerchantProfileResponse(

    @field:SerializedName("status_code")
	val statusCode: Int? = null,

    @field:SerializedName("data")
	val data: User? = null,

    @field:SerializedName("message")
	var message: String? = ""
)