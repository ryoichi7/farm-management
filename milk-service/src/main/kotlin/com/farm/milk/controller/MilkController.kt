package com.farm.milk.controller

import com.farm.milk.dto.MilkRecordRequest
import com.farm.milk.dto.MilkRecordResponse
import com.farm.milk.dto.MilkYieldAnalytics
import com.farm.milk.service.MilkService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/v1/milk")
class MilkController(
    private val milkService: MilkService
) {
    @PostMapping("/record")
    fun recordMilkYield(@RequestBody request: MilkRecordRequest): ResponseEntity<MilkRecordResponse> {
        val response = milkService.recordMilkYield(request)
        return ResponseEntity(response, HttpStatus.CREATED)
    }

    @GetMapping("/{animalId}/analytics")
    fun getMilkYieldAnalytics(
        @PathVariable animalId: String,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) startDate: LocalDateTime?,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) endDate: LocalDateTime?
    ): ResponseEntity<MilkYieldAnalytics> {
        val analytics = milkService.getMilkYieldAnalytics(
            animalId,
            startDate ?: LocalDateTime.now().minusHours(1),
            endDate ?: LocalDateTime.now()
        )
        return ResponseEntity.ok(analytics)
    }

    @GetMapping("/{animalId}/records")
    fun getMilkRecords(@PathVariable animalId: String): ResponseEntity<List<MilkRecordResponse>> {
        val records = milkService.getMilkRecords(animalId)
        return if (records.isEmpty()) {
            ResponseEntity.notFound().build()
        } else {
            ResponseEntity.ok(records)
        }
    }
} 