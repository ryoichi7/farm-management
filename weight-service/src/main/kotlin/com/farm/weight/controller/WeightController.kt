package com.farm.weight.controller

import com.farm.weight.dto.WeightAnalytics
import com.farm.weight.dto.WeightRecordRequest
import com.farm.weight.dto.WeightRecordResponse
import com.farm.weight.service.WeightService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/weights")
class WeightController(
    private val weightService: WeightService
) {
    @PostMapping
    fun recordWeight(@RequestBody request: WeightRecordRequest): ResponseEntity<WeightRecordResponse> {
        val response = weightService.recordWeight(request)
        return ResponseEntity(response, HttpStatus.CREATED)
    }

    @GetMapping("/{animalId}/latest")
    fun getLatestWeight(@PathVariable animalId: String): ResponseEntity<WeightRecordResponse> {
        return weightService.getLatestWeight(animalId)
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()
    }

    @GetMapping("/{animalId}/analytics")
    fun getWeightAnalytics(@PathVariable animalId: String): ResponseEntity<WeightAnalytics> {
        return weightService.getWeightAnalytics(animalId)
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()
    }

    @GetMapping("/{animalId}/history")
    fun getWeightHistory(@PathVariable animalId: String): ResponseEntity<List<WeightRecordResponse>> {
        val history = weightService.getWeightHistory(animalId)
        return if (history.isEmpty()) {
            ResponseEntity.notFound().build()
        } else {
            ResponseEntity.ok(history)
        }
    }
} 