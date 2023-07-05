package com.didaagency.adopteunelivraison.data.network.response.merchant

import com.google.gson.annotations.SerializedName

data class PayoutHistory(
    @field:SerializedName("transaction_price")
    val transactionPrice: String? = null,
    @field:SerializedName("transaction_status")
    val transactionStatus: String? = null,
    @field:SerializedName("transaction_date")
    val transactionDate: String? = null,
    @field:SerializedName("transaction_details")
val transactionDetails: String? = null,

    ) {
}