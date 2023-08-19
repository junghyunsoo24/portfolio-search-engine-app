package com.example.child_emotion_app.data.noAllocateChildList

data class NoAllocateChildListResponse (
    val child: List<Child>
)

data class Child(
    val id: String,
    val name: String,
    val phone_num: String,
    val address: String,
    val sentiment: String
)
