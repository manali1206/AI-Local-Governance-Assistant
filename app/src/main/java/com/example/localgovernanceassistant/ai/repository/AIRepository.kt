package com.example.localgovernanceassistant.ai.repository

class AIRepository {

    fun getResponse(question: String): String {
        return "I received your question: $question"
    }
}