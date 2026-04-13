package com.cristiano.livvichallenge.data.repository

import com.cristiano.livvichallenge.core.network.ApiService
import com.cristiano.livvichallenge.data.mapper.toDomain
import com.cristiano.livvichallenge.domain.model.DoorEvent
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DoorEventsRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getDoorEvents(
        doorId: Int,
        page: Int = 0,
        size: Int = 20
    ): List<DoorEvent> {
        val response = apiService.getDoorEvents(
            doorId = doorId,
            page = page,
            size = size
        )

//        Log.d("DoorEventsRepository", "response = $response")

        return response.content
            // Garantindo ordem mais recente primeiro
            .sortedByDescending { it.id }
            .map { it.toDomain() }
    }


}