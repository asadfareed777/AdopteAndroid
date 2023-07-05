package com.didaagency.adopteunelivraison.data.network.response.merchant

import com.google.gson.annotations.SerializedName

data class TransactionHistory(
    @field:SerializedName("total_earning")
    val totalEarning: String? = null,
    @field:SerializedName("transaction_details")
    val transactionDetails: String? = null,
    @field:SerializedName("transaction_date")
    val transactionDate: String? = null,
    @field:SerializedName("order_price")
    val orderPrice: String? = null

    ) {
}