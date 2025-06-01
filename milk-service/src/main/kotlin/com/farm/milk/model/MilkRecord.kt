package com.farm.milk.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "milk_records")
data class MilkRecord(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val animalId: String,

    @Column(nullable = false)
    val milkAmount: Double,

    @Column(nullable = true)
    var yieldPerKg: Double? = null,

    @Column(nullable = false)
    val recordedAt: LocalDateTime = LocalDateTime.now()
) 