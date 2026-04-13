package com.cristiano.livvichallenge.data.repository

import com.cristiano.livvichallenge.core.network.ApiService
import com.cristiano.livvichallenge.data.mapper.toDomain
import com.cristiano.livvichallenge.domain.model.Door
import com.cristiano.livvichallenge.domain.model.DoorPagedResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DoorsRepository  @Inject constructor (
    private val apiService: ApiService
) {
    suspend fun getDoors(
        page: Int = 0,
        size: Int = 10
    ): DoorPagedResult<Door>{
        val response= apiService.getDoors(page = page, size = size)
        return DoorPagedResult(
            items=response.content.map{it.toDomain()},
            currentPage = response.page,
            totalPages = response.totalPages,
            isLastPage = response.last
        )

    }

    suspend fun findDoors(
        name: String,
        page: Int = 0,
        size: Int = 10
    ): DoorPagedResult<Door>  {
        val response = apiService.findDoors(
            name = name,
            page = page,
            size = size
        )
        return DoorPagedResult(
            items=response.content.map{it.toDomain()},
            currentPage = response.page,
            totalPages = response.totalPages,
            isLastPage = response.last
        )


    }

    suspend fun getDoorById(doorId: Int): Door {
        return apiService.getDoorById(doorId).toDomain()
    }
}






