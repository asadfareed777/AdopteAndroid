package com.didaagency.adopteunelivraison.data.database.dao

import androidx.room.*
import com.didaagency.adopteunelivraison.data.database.entities.UnsentData
import com.didaagency.adopteunelivraison.utils.API


@Dao
interface UnsentDataDao {
    @Delete
    suspend fun delete(unsentData: UnsentData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(unsentDataList: List<UnsentData>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(unsentData: UnsentData)

    @Update
    suspend fun update(unsentData: UnsentData)

    @Query("delete from UnsentData")
    suspend fun deleteAll()

    @Query("delete from UnsentData where :data=json")
    suspend fun deleteSentData(data:String)

    @Query("Select * from UnsentData")
    suspend fun getAll(): List<UnsentData>

    @Query("Select * from UnsentData where :param= api")
    suspend fun getAllUnsentData(param:API): List<UnsentData>

    @Query("Select * from UnsentData")
    suspend fun getAllUnsentData(): List<UnsentData>
}