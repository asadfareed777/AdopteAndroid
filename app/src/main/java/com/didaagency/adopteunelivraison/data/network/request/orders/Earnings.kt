package com.didaagency.adopteunelivraison.data.network.request.orders

import com.google.gson.annotations.SerializedName

data class Earnings(

    @field:SerializedName("title")
    var title: String,

    @field:SerializedName("earnings")
    var earnings: String,

    @field:SerializedName("bg_color")
    var bgColor: String
    )