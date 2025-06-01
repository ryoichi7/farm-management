package com.farm.milk.service

import com.farm.milk.dto.MilkRecordRequest
import com.farm.milk.dto.MilkRecordResponse
import com.farm.milk.dto.MilkYieldAnalytics
import com.farm.milk.model.MilkRecord
import com.farm.milk.repository.MilkRecordRepository
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class MilkService(
    private val milkRecordRepository: MilkRecordRepository,
    private val weightServiceClient: WeightServiceClient
) {
    @Transactional
    fun recordMilkYield(request: MilkRecordRequest): MilkRecordResponse = runBlocking {
        val animalWeight = weightServiceClient.getLatestWeight(request.animalId)
        
        val milkRecord = MilkRecord(
            animalId = request.animalId,
            milkAmount = request.milkAmount,
            yieldPerKg = animalWeight?.let { request.milkAmount / it }
        )
        
        val savedRecord = milkRecordRepository.save(milkRecord)
        return@runBlocking savedRecord.toResponse()
    }

    @Transactional(readOnly = true)
    fun getMilkYieldAnalytics(
        animalId: String,
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ): MilkYieldAnalytics {
        val records = milkRecordRepository.findByAnimalIdAndRecordedAtBetweenOrderByRecordedAtDesc(
            animalId, startDate, endDate
        )
        
        val totalMilkAmount = milkRecordRepository.calculateTotalMilkAmount(animalId, startDate, endDate) ?: 0.0
        val averageYieldPerKg = milkRecordRepository.calculateAverageYieldPerKg(animalId, startDate, endDate)

        return MilkYieldAnalytics(
            animalId = animalId,
            totalMilkAmount = totalMilkAmount,
            averageYieldPerKg = averageYieldPerKg,
            recordsCount = records.size,
            periodStart = startDate,
            periodEnd = endDate
        )
    }

    @Transactional(readOnly = true)
    fun getMilkRecords(animalId: String): List<MilkRecordResponse> {
        return milkRecordRepository.findByAnimalIdOrderByRecordedAtDesc(animalId)
            .map { it.toResponse() }
    }

    private fun MilkRecord.toResponse() = MilkRecordResponse(
        id = id,
        animalId = animalId,
        milkAmount = milkAmount,
        yieldPerKg = yieldPerKg,
        recordedAt = recordedAt
    )
} 