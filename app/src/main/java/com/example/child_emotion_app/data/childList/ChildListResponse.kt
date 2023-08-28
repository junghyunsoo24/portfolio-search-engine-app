package com.example.child_emotion_app.data.childList

data class ChildListResponse(
    val result: List<Child>
)

data class Child(
    val id: String,
    val pw: String,
    val name: String,
    val age: Int,
    val address: String,
    val phone_num: String,
    val at_risk_child_status: Int,
    val sentiment: Int
)