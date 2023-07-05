package com.didaagency.adopteunelivraison.data.network.response.login

import com.didaagency.adopteunelivraison.data.network.response.User
import com.google.gson.annotations.SerializedName

data class LoginData(

	@field:SerializedName("access_token")
	val accessToken: String? = null,

	@field:SerializedName("user")
	val user: User? = null
)