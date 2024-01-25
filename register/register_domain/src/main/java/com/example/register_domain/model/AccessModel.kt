package com.example.register_domain.model

import java.io.Serializable

data class AccessModel(
    val user: String,
    val refresh: String,
    val access: String,
    val refreshLifetimeDays: Int,
    val accessLifetimeDays: Int
):Serializable