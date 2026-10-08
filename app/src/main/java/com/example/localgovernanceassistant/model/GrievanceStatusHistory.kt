package com.example.localgovernanceassistant.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GrievanceStatusHistory(
    val id: String? = null,

    @SerialName("grievance_id")
    val grievanceId: String,

    @SerialName("old_status")
    val oldStatus: String,

    @SerialName("new_status")
    val newStatus: String,

    @SerialName("changed_at")
    val changedAt: String
)