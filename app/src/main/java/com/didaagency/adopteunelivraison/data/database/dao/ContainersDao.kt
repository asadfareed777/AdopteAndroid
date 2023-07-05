package com.didaagency.adopteunelivraison.data.database.dao

import androidx.room.*
import com.didaagency.adopteunelivraison.data.network.response.ContainerData


@Dao
interface ContainersDao {
    @Delete
    suspend fun delete(containerData: ContainerData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(containerDataList: List<ContainerData>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(containerData: ContainerData)

    @Update
    suspend fun update(containerData: ContainerData)

    @Query("delete from ContainerData")
    suspend fun deleteAll()

    @Query("Select * from ContainerData")
    suspend fun getAll(): List<ContainerData>

}