package com.example.profile_data.model

data class ProfileDataModel(
    val id: Int,
    val image: String,
    val completedLesson: Int,
    val completedTest: Int,
    val points: Int,
    val user: Int,
    val rank: String?,  // Здесь предполагается, что rank может быть null
    val totalLessons: Int,
    val totalQuestions: Int
)
