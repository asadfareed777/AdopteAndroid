package com.didaagency.adopteunelivraison.data.network.response

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "ContainerData")
data class ContainerData(
    @PrimaryKey
    @SerializedName("container_id") var containerId: String,
    @SerializedName("name") var name: String? = null,
    @SerializedName("capacity") var capacity: String? = null,
    @SerializedName("area_name") var areaName: String? = null,
    @SerializedName("district") var district: String? = null,
    @SerializedName("conntainer_condition") var conntainerCondition: String? = null,
    @SerializedName("union_coucnil") var unionCoucnil: String? = null,
    @SerializedName("location") var location: String? = null
){
    override fun toString(): String {
        return name?:"N/A"
    }
}