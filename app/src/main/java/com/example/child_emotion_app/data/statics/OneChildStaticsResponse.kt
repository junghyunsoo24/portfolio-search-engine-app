package com.example.child_emotion_app.data.statics

data class OneChildStaticsResponse(
    val statistics: List<Emotion>
)

data class Emotion(
    val date: String,
    val pleasure: String,
    val anxiety: String,
    val sorrow: String,
    val embarrassed: String,
    val anger: String,
    val hurt: String
)