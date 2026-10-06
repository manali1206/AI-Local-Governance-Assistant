package com.example.localgovernanceassistant.model

import com.google.gson.annotations.SerializedName

data class Grievance(

    val id: String? = null,

    @SerializedName("reference_id")
    val referenceId: String? = null,

    @SerializedName("user_id")
    val userId: String,

    val title: String,

    val description: String,

    val status: String = "Pending",

    @SerializedName("created_at")
    val createdAt: String? = null
)