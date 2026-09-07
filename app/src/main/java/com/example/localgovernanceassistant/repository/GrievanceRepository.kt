package com.example.localgovernanceassistant.repository

import com.example.localgovernanceassistant.model.Grievance
import com.example.localgovernanceassistant.supabaseClient
import io.github.jan.supabase.postgrest.from


class GrievanceRepository {

    suspend fun submitGrievance(
        grievance: Grievance
    ) {
        supabaseClient
            .from("Grievances")
            .insert(grievance)
    }

    suspend fun getUserGrievances(
        userId: String
    ): List<Grievance> {

        return supabaseClient
            .from("Grievances")
            .select {
                filter {
                    eq("user_id", userId)
                }

            }
            .decodeList<Grievance>()
            .sortedByDescending { it.createdAt }
    }
}