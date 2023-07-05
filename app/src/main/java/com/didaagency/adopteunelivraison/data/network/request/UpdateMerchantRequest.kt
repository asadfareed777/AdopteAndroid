package com.didaagency.adopteunelivraison.data.network.request

import com.google.gson.annotations.SerializedName

data class UpdateMerchantRequest(

    @field:SerializedName("_method")
    var method: String,
    @field:SerializedName("contact_name")
    var name: String,
    @field:SerializedName("contact_email")
    var email: String,
    @field:SerializedName("address")
    var address: String,
    @field:SerializedName("contact_phone")
    var contactPhone: String,
    @field:SerializedName("password")
    var password: String,
    @field:SerializedName("merchant_type")
    var merchantType: String
)