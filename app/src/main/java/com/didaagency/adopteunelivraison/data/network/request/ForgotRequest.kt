package com.didaagency.adopteunelivraison.data.network.request

import com.google.gson.annotations.SerializedName

data class ForgotRequest(

    @field:SerializedName("contact_email")
    var email: String
)