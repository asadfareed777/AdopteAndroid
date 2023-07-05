package com.didaagency.adopteunelivraison.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.didaagency.adopteunelivraison.utils.API

@Entity(tableName = "UnsentData")
data class UnsentData(
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0,
    var json: String,
    var api: API? = null,

)