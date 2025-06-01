package com.farm.weight.repository

import com.farm.weight.model.WeightRecord
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface WeightRecordRepository : JpaRepository<WeightRecord, Long> {
    fun findByAnimalIdOrderByMeasurementTimeDesc(animalId: String): List<WeightRecord>
} 