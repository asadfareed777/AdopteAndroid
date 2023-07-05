package com.didaagency.adopteunelivraison.data.network.request.merchant

import com.google.gson.annotations.SerializedName

data class MerchantMenu(

	@field:SerializedName("image")
	val image: Int? = null,

	@field:SerializedName("title")
	val title: String? = null
)
