
package com.example.localgovernanceassistant

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

suspend fun askGemini(question: String): String =
    withContext(Dispatchers.IO) {
        val url = URL("http://127.0.0.1:8000/api/ask")
        val connection = url.openConnection() as HttpURLConnection

        try {
            connection.requestMethod = "POST"

            connection.setRequestProperty(
                "Content-Type",
                "application/json; charset=UTF-8"
            )

            connection.setRequestProperty(
                "Accept",
                "application/json"
            )

            connection.connectTimeout = 10000
            connection.readTimeout = 30000

            connection.doOutput = true

            val requestBody = JSONObject()
                .put("question", question)
                .toString()

            val requestBytes = requestBody.toByteArray(Charsets.UTF_8)

            connection.outputStream.use { output ->
                output.write(requestBytes)
                output.flush()
            }

            val responseCode = connection.responseCode

            if (responseCode == 200) {

                val response = connection.inputStream
                    .bufferedReader(Charsets.UTF_8)
                    .readText()

                JSONObject(response).getString("answer")

            } else {

                val errorResponse = try {
                    connection.errorStream
                        ?.bufferedReader(Charsets.UTF_8)
                        ?.readText()
                } catch (e: Exception) {
                    null
                }

                "Error: Server returned $responseCode\n$errorResponse"
            }

        } catch (e: Exception) {

            "Connection error: ${e.message}"

        } finally {

            connection.disconnect()
        }
    }

