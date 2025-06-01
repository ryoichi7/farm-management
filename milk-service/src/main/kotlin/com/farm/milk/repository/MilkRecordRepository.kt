package com.farm.milk.repository

import com.farm.milk.model.MilkRecord
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface MilkRecordRepository : JpaRepository<MilkRecord, Long> {
    fun findByAnimalIdOrderByRecordedAtDesc(animalId: String): List<MilkRecord>
    
    fun findByAnimalIdAndRecordedAtBetweenOrderByRecordedAtDesc(
        animalId: String,
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ): List<MilkRecord>

    @Query("SELECT SUM(m.milkAmount) FROM MilkRecord m WHERE m.animalId = :animalId AND m.recordedAt BETWEEN :startDate AND :endDate")
    fun calculateTotalMilkAmount(animalId: String, startDate: LocalDateTime, endDate: LocalDateTime): Double?

    @Query("SELECT AVG(m.yieldPerKg) FROM MilkRecord m WHERE m.animalId = :animalId AND m.recordedAt BETWEEN :startDate AND :endDate")
    fun calculateAverageYieldPerKg(animalId: String, startDate: LocalDateTime, endDate: LocalDateTime): Double?
} 