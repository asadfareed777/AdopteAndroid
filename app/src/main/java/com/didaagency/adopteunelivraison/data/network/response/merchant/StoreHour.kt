package com.didaagency.adopteunelivraison.data.network.response.merchant

import com.google.gson.annotations.SerializedName

data class StoreHour(
    @field:SerializedName("isOpenColse")
    var isOpenColse: Boolean = false,
    @field:SerializedName("day")
    var day: String? = null,
    @field:SerializedName("from")
    var from: String? = null,
    @field:SerializedName("to")
    var to: String? = null,
    @field:SerializedName("message")
    var message: String? = null
) {
}