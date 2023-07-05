package com.didaagency.adopteunelivraison.data.network.response.login

import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @field:SerializedName("status_code")
	val statusCode: Int? = null,

    @field:SerializedName("data")
	val data: LoginData? = null,

    @field:SerializedName("message")
	var message: String? = ""
)