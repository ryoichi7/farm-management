package com.farm.weight.dto

import java.time.LocalDateTime

data class WeightRecordRequest(
    val animalId: String,
    val weight: Double
)

data class WeightRecordResponse(
    val id: Long,
    val animalId: String,
    val weight: Double,
    val measurementTime: LocalDateTime
)

data class WeightAnalytics(
    val animalId: String,
    val currentWeight: Double,
    val previousWeight: Double?,
    val weightDifference: Double?,
    val measurementTime: LocalDateTime
) 