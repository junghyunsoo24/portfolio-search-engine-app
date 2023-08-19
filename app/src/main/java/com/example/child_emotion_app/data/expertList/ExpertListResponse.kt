package com.example.child_emotion_app.data.expertList

data class ExpertListResponse(
    val expert: List<Expert>
)

data class Expert(
    val name: String,
    val institution: String
)