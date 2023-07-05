package com.didaagency.adopteunelivraison.data.network.request

import com.google.gson.annotations.SerializedName

data class LoginRequest(

    @field:SerializedName("username")
    var username: String,
    @field:SerializedName("password")
    var password: String,
    @field:SerializedName("login_type")
    var login_type: String
)