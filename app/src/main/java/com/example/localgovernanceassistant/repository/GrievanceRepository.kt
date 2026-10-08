package com.example.localgovernanceassistant.repository

import com.example.localgovernanceassistant.model.Grievance
import com.example.localgovernanceassistant.model.GrievanceStatusHistory
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

    suspend fun getGrievanceByReferenceId(
        userId: String,
        referenceId: String
    ): Grievance? {

        return supabaseClient
            .from("Grievances")
            .select {
                filter {
                    eq("user_id", userId)
                    eq("reference_id", referenceId)
                }
            }
            .decodeList<Grievance>()
            .firstOrNull()
    }

    suspend fun getGrievanceStatusHistory(
        userId: String,
        referenceId: String
    ): List<GrievanceStatusHistory> {

        // First verify that the grievance belongs to the logged-in user.
        val grievance = getGrievanceByReferenceId(
            userId = userId,
            referenceId = referenceId
        ) ?: return emptyList()

        val grievanceId = grievance.id ?: return emptyList()

        return supabaseClient
            .from("grievance_status_history")
            .select {
                filter {
                    eq("grievance_id", grievanceId)
                }
            }
            .decodeList<GrievanceStatusHistory>()
            .sortedBy { it.changedAt }
    }
}