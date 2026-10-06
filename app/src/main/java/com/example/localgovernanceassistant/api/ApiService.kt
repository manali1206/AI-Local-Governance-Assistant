package com.example.localgovernanceassistant.api

import com.example.localgovernanceassistant.model.Grievance
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

data class HealthResponse(
    val status: String
)

data class StatusUpdateRequest(
    val status: String
)

data class StatusUpdateResponse(
    val status: String,
    val message: String,
    val old_status: String? = null,
    val new_status: String? = null
)

interface ApiService {

    @GET("health")
    suspend fun checkHealth(): HealthResponse

    @POST("api/grievances")
    suspend fun submitGrievance(
        @Body grievance: Grievance
    ): Grievance

    @GET("api/grievances/{userId}")
    suspend fun getUserGrievances(
        @Path("userId") userId: String
    ): List<Grievance>

    @PATCH("grievances/{grievanceId}/status")
    suspend fun updateGrievanceStatus(
        @Path("grievanceId") grievanceId: String,
        @Header("Authorization") authorization: String,
        @Body request: StatusUpdateRequest
    ): StatusUpdateResponse
}