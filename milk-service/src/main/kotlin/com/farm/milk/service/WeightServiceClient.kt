package com.farm.milk.service

import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody

@Service
class WeightServiceClient(
    private val weightServiceClient: WebClient
) {
    suspend fun getLatestWeight(animalId: String): Double? {
        return try {
            weightServiceClient.get()
                .uri("/api/v1/weights/{animalId}/latest", animalId)
                .retrieve()
                .awaitBody<WeightResponse>()
                .weight
        } catch (e: Exception) {
            null
        }
    }
}

data class WeightResponse(
    val id: Long,
    val animalId: String,
    val weight: Double
) 
 