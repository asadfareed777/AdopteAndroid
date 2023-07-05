package com.didaagency.adopteunelivraison.data.network.response.merchant

import com.google.gson.annotations.SerializedName

data class YearlySalesByMonths(

	@field:SerializedName("June")
	val june: Int? = null,

	@field:SerializedName("October")
	val october: Int? = null,

	@field:SerializedName("December")
	val december: Int? = null,

	@field:SerializedName("May")
	val may: Int? = null,

	@field:SerializedName("September")
	val september: Int? = null,

	@field:SerializedName("March")
	val march: Int? = null,

	@field:SerializedName("July")
	val july: Int? = null,

	@field:SerializedName("January")
	val january: Int? = null,

	@field:SerializedName("February")
	val february: Int? = null,

	@field:SerializedName("April")
	val april: Int? = null,

	@field:SerializedName("August")
	val august: Int? = null,

	@field:SerializedName("November")
	val november: Int? = null
)