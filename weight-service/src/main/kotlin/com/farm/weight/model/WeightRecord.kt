package com.farm.weight.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "weight_records")
data class WeightRecord(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val animalId: String,

    @Column(nullable = false)
    val weight: Double,

    @Column(nullable = false)
    val measurementTime: LocalDateTime = LocalDateTime.now()
) 