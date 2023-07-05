package com.didaagency.adopteunelivraison.data.network.request

import com.google.gson.annotations.SerializedName

data class SaveContainerRequest(
    @SerializedName("Imei")
    var imei: String,
    @SerializedName("container_id")
    var containerId: String,
    @SerializedName("location")
    var location: String,
    @SerializedName("picture")
    var picture: String,
    @SerializedName("datetime")
    var datetime: String,
    @SerializedName("condition")
    var condition: String,
    @SerializedName("area_name")
    var areaName: String,
    @SerializedName("app_version")
    var appVersion: String,
    @SerializedName("remarks")
    var remarks: String,
    @SerializedName("added_by")
    var addedBy: String,
)