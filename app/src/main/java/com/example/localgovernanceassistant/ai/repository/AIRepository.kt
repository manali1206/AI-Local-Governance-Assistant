package com.example.localgovernanceassistant.ai.repository

import com.example.localgovernanceassistant.ai.model.ChatMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class AIRepository {

    private val client = OkHttpClient()

    suspend fun sendMessage(message: String): ChatMessage =
        withContext(Dispatchers.IO) {

            val json = JSONObject()
                .put("message", message)

            val requestBody = json.toString()
                .toRequestBody("application/json".toMediaType())

            val request = Request.Builder()
                .url("http://10.0.2.2:8000/chat")
                .post(requestBody)
                .build()

            client.newCall(request).execute().use { response ->

                if (!response.isSuccessful) {
                    throw Exception("HTTP ${response.code}")
                }

                val responseBody = response.body?.string()
                    ?: throw Exception("Empty response")

                val responseJson = JSONObject(responseBody)

                ChatMessage(
                    message = responseJson.getString("response"),
                    isUser = false
                )
            }
        }
}