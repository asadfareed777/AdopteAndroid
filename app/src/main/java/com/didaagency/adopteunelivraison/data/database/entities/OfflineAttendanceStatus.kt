package com.didaagency.adopteunelivraison.data.database.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.didaagency.adopteunelivraison.utils.API
import com.didaagency.adopteunelivraison.utils.AttendanceType

@Entity(tableName = "OfflineAttendanceStatus",indices = [Index(value = ["employeeId"], unique = true)])
data class OfflineAttendanceStatus(
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0,
    var name: String,
    var employeeCode: String,
    var employeeId: String,
    var attendanceType: AttendanceType? = null,

)