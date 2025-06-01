package com.farm.milk.dto

import java.time.LocalDateTime

data class MilkRecordRequest(
    val animalId: String,
    val milkAmount: Double
)

data class MilkRecordResponse(
    val id: Long,
    val animalId: String,
    val milkAmount: Double,
    val yieldPerKg: Double?,
    val recordedAt: LocalDateTime
)

data class MilkYieldAnalytics(
    val animalId: String,
    val totalMilkAmount: Double,
    val averageYieldPerKg: Double?,
    val recordsCount: Int,
    val periodStart: LocalDateTime,
    val periodEnd: LocalDateTime
) 