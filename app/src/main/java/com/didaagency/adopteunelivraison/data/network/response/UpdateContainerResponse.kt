package com.didaagency.adopteunelivraison.data.network.response

import com.google.gson.annotations.SerializedName

data class UpdateContainerResponse (
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("errorCode")
    val errorCode: String,
    @SerializedName("message")
    val message: String
)