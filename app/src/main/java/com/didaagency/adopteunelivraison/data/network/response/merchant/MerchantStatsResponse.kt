package com.didaagency.adopteunelivraison.data.network.response.merchant

import com.google.gson.annotations.SerializedName

data class MerchantStatsResponse(

	@field:SerializedName("total_earning")
	val totalEarning: Int? = null,

	@field:SerializedName("monthly_refunded")
	val monthlyRefunded: Int? = null,

	@field:SerializedName("week_earning")
	val weekEarning: Int? = null,

	@field:SerializedName("monthly_cancelled")
	val monthlyCancelled: Int? = null,

	@field:SerializedName("yearly_sales_by_months")
	val yearlySalesByMonths: YearlySalesByMonths? = null,

	@field:SerializedName("monthly_earning")
	val monthlyEarning: Int? = null
)