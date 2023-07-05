package com.didaagency.adopteunelivraison.data.network.response

import com.google.gson.annotations.SerializedName


data class SyncContainersResponse(
    @SerializedName("status") var status: Boolean? = null,
    @SerializedName("errorCode") var errorCode: String? = null,
    @SerializedName("message") var message: String? = null,
    @SerializedName("Container_Data") var containerData: ArrayList<ContainerData> = arrayListOf()
)