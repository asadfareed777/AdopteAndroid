package com.didaagency.adopteunelivraison.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.android.framework.mvvm.data.database.typeconverters.Convertors
import com.didaagency.adopteunelivraison.data.database.dao.*
import com.didaagency.adopteunelivraison.data.database.entities.OfflineAttendanceStatus
import com.didaagency.adopteunelivraison.data.database.entities.UnsentData
import com.didaagency.adopteunelivraison.data.network.response.ContainerData

@Database(
    entities = [ContainerData::class, UnsentData::class, OfflineAttendanceStatus::class],
    version = 1
)
@TypeConverters(Convertors::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun containersDao(): ContainersDao
    abstract fun unsentDataDao(): UnsentDataDao
}