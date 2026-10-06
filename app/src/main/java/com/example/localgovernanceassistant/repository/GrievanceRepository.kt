package com.example.localgovernanceassistant.repository

import com.example.localgovernanceassistant.api.RetrofitClient
import com.example.localgovernanceassistant.model.Grievance

class GrievanceRepository {

    private val apiService = RetrofitClient.apiService

    suspend fun submitGrievance(
        grievance: Grievance
    ): Grievance {
        return apiService.submitGrievance(grievance)
    }

    suspend fun getUserGrievances(
        userId: String
    ): List<Grievance> {
        return apiService.getUserGrievances(userId)
    }
}