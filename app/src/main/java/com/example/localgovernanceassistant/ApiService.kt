
package com.example.localgovernanceassistant

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

// ==============================
// AI ASSISTANT
// ==============================

data class AskRequest(
    val question: String
)

data class AskResponse(
    val status: String? = null,
    val answer: String
)


// ==============================
// GOVERNMENT SCHEMES
// ==============================

data class Scheme(
    val name: String,
    val category: String,
    val purpose: String,
    val benefits: String,
    val eligibility: String,
    val application: String
)

data class SchemesResponse(
    val status: String? = null,
    val schemes: List<Scheme>
)


// ==============================
// GRIEVANCE
// ==============================

data class GrievanceRequest(
    val user_id: String,
    val title: String,
    val description: String
)

data class GrievanceResponse(
    val id: String? = null,
    val reference_id: String? = null,
    val user_id: String,
    val title: String,
    val description: String,
    val status: String? = null,
    val created_at: String? = null
)


// ==============================
// CREATE GRIEVANCE RESPONSE
// Backend:
// {
//   "status": "success",
//   "message": "...",
//   "grievance": {...}
// }
// ==============================

data class CreateGrievanceResponse(
    val status: String? = null,
    val message: String? = null,
    val grievance: GrievanceResponse
)


// ==============================
// GET USER GRIEVANCES RESPONSE
// Backend:
// {
//   "status": "success",
//   "grievances": [...]
// }
// ==============================

data class UserGrievancesResponse(
    val status: String? = null,
    val grievances: List<GrievanceResponse>
)


// ==============================
// UPDATE GRIEVANCE STATUS RESPONSE
// Backend:
// {
//   "status": "success",
//   "message": "...",
//   "grievance": {...}
// }
// ==============================

data class UpdateGrievanceResponse(
    val status: String? = null,
    val message: String? = null,
    val grievance: GrievanceResponse
)


// ==============================
// API SERVICE
// ==============================

interface ApiService {

    // ------------------------------
    // AI ASSISTANT
    // ------------------------------

    @POST("api/ask")
    suspend fun askAI(
        @Body request: AskRequest
    ): Response<AskResponse>


    // ------------------------------
    // GOVERNMENT SCHEMES
    // ------------------------------

    @GET("api/schemes")
    suspend fun getSchemes(): Response<SchemesResponse>


    // ------------------------------
    // HEALTH CHECK
    // ------------------------------

    @GET("health")
    suspend fun healthCheck(): Response<Map<String, String>>


    // ------------------------------
    // CREATE GRIEVANCE
    // ------------------------------

    @POST("api/grievances")
    suspend fun submitGrievance(
        @Body request: GrievanceRequest
    ): Response<CreateGrievanceResponse>


    // ------------------------------
    // GET USER GRIEVANCES
    // ------------------------------

    @GET("api/grievances/{user_id}")
    suspend fun getUserGrievances(
        @Path("user_id") userId: String
    ): Response<UserGrievancesResponse>


    // ------------------------------
    // UPDATE GRIEVANCE STATUS
    // ------------------------------

    @PATCH("api/grievances/{grievance_id}/status")
    suspend fun updateGrievanceStatus(
        @Path("grievance_id") grievanceId: String,
        @Body status: Map<String, String>
    ): Response<UpdateGrievanceResponse>
}

