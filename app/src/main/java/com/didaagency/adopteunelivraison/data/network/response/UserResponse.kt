package com.didaagency.adopteunelivraison.data.network.response

import com.google.gson.annotations.SerializedName

data class UserResponse (
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("errorCode")
    val errorCode: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("Data")
    val userData: User,
)