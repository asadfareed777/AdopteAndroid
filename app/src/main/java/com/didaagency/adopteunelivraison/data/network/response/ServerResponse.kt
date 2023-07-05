package com.didaagency.adopteunelivraison.data.network.response

import com.google.gson.annotations.SerializedName

data class ServerResponse(
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("status_code")
    val statusCode: Int,
    @SerializedName("message")
    val message: String,
)