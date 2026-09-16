package com.example.localgovernanceassistant.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Grievance(
    val id: String? = null,

    @SerialName("reference_id")
    val referenceId: String? = null,

    @SerialName("user_id")
    val userId: String,

    val title: String,

    val description: String,

    val status: String = "Pending",

    @SerialName("created_at")
    val createdAt: String? = null
)