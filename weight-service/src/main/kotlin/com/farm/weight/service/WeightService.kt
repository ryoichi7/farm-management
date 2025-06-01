package com.farm.weight.service

import com.farm.weight.dto.WeightAnalytics
import com.farm.weight.dto.WeightRecordRequest
import com.farm.weight.dto.WeightRecordResponse
import com.farm.weight.model.WeightRecord
import com.farm.weight.repository.WeightRecordRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class WeightService(
    private val weightRecordRepository: WeightRecordRepository
) {
    @Transactional
    fun recordWeight(request: WeightRecordRequest): WeightRecordResponse {
        val weightRecord = WeightRecord(
            animalId = request.animalId,
            weight = request.weight
        )
        val savedRecord = weightRecordRepository.save(weightRecord)
        return savedRecord.toResponse()
    }

    @Transactional(readOnly = true)
    fun getLatestWeight(animalId: String): WeightRecordResponse? {
        return weightRecordRepository.findByAnimalIdOrderByMeasurementTimeDesc(animalId)
            .firstOrNull()
            ?.toResponse()
    }

    @Transactional(readOnly = true)
    fun getWeightAnalytics(animalId: String): WeightAnalytics? {
        val weightRecords = weightRecordRepository.findByAnimalIdOrderByMeasurementTimeDesc(animalId)
        if (weightRecords.isEmpty()) return null

        val currentRecord = weightRecords[0]
        val previousRecord = weightRecords.getOrNull(1)

        return WeightAnalytics(
            animalId = currentRecord.animalId,
            currentWeight = currentRecord.weight,
            previousWeight = previousRecord?.weight,
            weightDifference = previousRecord?.let { currentRecord.weight - it.weight },
            measurementTime = currentRecord.measurementTime
        )
    }

    @Transactional(readOnly = true)
    fun getWeightHistory(animalId: String): List<WeightRecordResponse> {
        return weightRecordRepository.findByAnimalIdOrderByMeasurementTimeDesc(animalId)
            .map { it.toResponse() }
    }

    private fun WeightRecord.toResponse() = WeightRecordResponse(
        id = id,
        animalId = animalId,
        weight = weight,
        measurementTime = measurementTime
    )
} 