package com.example.child_emotion_app.data.childList

data class ChildListResponse(
    val child: List<Child>
)

data class Child(
    val id: String,
    val pw: String,
    val name: String,
    val email: String,
    val institution: String
)