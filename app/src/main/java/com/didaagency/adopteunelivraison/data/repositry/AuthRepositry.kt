package com.didaagency.adopteunelivraison.data.repositry

import com.didaagency.adopteunelivraison.data.database.AppDatabase
import com.didaagency.adopteunelivraison.data.database.entities.OfflineAttendanceStatus
import com.didaagency.adopteunelivraison.data.database.entities.UnsentData
import com.didaagency.adopteunelivraison.data.network.ApiHelper
import com.didaagency.adopteunelivraison.data.network.ApiService
import com.didaagency.adopteunelivraison.data.network.request.ForgotRequest
import com.didaagency.adopteunelivraison.data.network.request.LoginRequest
import com.didaagency.adopteunelivraison.data.network.request.RegisterRequest
import com.didaagency.adopteunelivraison.data.network.response.ContainerData
import com.didaagency.adopteunelivraison.utils.API
import com.didaagency.adopteunelivraison.utils.AppUtils
import com.didaagency.adopteunelivraison.utils.AttendanceType
import javax.inject.Inject


class AuthRepository @Inject constructor(
    private val apiHelper: ApiService,
    private val appDatabase: AppDatabase,
    private val appUtils: AppUtils
    ) {

    suspend fun login(data:LoginRequest) = apiHelper.login(appUtils.getDefaultHeaders(false),data)

    suspend fun register(data:RegisterRequest) = apiHelper.register(appUtils.getDefaultHeaders(false),data)

    suspend fun forgotPassword(data:ForgotRequest) = apiHelper.forgotPassword(appUtils.getDefaultHeaders(false),data)

    suspend fun syncUcContainers(imei:String,uc_id:String,district_id:String,app_version:String) =
        apiHelper.syncUcContainers(imei,uc_id, district_id, app_version)

    suspend fun saveContainers(containerDataList: List<ContainerData>) {
        appDatabase.containersDao().deleteAll()
        appDatabase.containersDao().insertAll(containerDataList)
    }

    suspend fun clearSentData(json:String) {
        appDatabase.unsentDataDao().deleteSentData(json)
    }


    suspend fun saveUnsentData(unsentData: UnsentData) {
        appDatabase.unsentDataDao().insert(unsentData)
    }


    suspend fun getContainerList():List<ContainerData> {
        return appDatabase.containersDao().getAll()
    }


    suspend fun getContainerUnsentList():List<UnsentData> {
        return appDatabase.unsentDataDao().getAllUnsentData(API.CONTAINER)
    }

    suspend fun getEnrollUnsentList():List<UnsentData> {
        return appDatabase.unsentDataDao().getAllUnsentData(API.ENROLL)
    }

    suspend fun getUnsentList():List<UnsentData> {
        return appDatabase.unsentDataDao().getAllUnsentData()
    }

    suspend fun clearAllTableData() {
         appDatabase.containersDao().deleteAll()
         appDatabase.unsentDataDao().deleteAll()
    }


}