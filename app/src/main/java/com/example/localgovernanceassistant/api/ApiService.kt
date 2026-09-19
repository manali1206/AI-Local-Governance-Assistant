package com.example.localgovernanceassistant.api

import retrofit2.http.GET

data class HealthResponse(
    val status: String
)

interface ApiService {

    @GET("health")
    suspend fun checkHealth(): HealthResponse
}