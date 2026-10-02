package com.example.localgovernanceassistant.api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Header
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

    @PATCH("grievances/{grievanceId}/status")
    suspend fun updateGrievanceStatus(
        @Path("grievanceId") grievanceId: String,
        @Header("Authorization") authorization: String,
        @Body request: StatusUpdateRequest
    ): StatusUpdateResponse
}