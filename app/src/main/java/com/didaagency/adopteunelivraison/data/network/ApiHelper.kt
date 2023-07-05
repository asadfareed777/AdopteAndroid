package com.didaagency.adopteunelivraison.data.network

import com.didaagency.adopteunelivraison.data.network.request.*
import com.didaagency.adopteunelivraison.data.network.response.*
import retrofit2.Response
import javax.inject.Inject

interface ApiHelper {
    suspend fun login(data:LoginRequest): UserResponse
    suspend fun updateContainer(containerRequest: SaveContainerRequest): UpdateContainerResponse
    suspend fun syncUcContainers(imei:String,uc_id:String,district_id:String,app_version:String): SyncContainersResponse
}

