package com.cristiano.livvichallenge.data.dto

data class DoorDto(
    val id: Int,
    val serial: String,
    val lockMac: String,
    val name: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val battery: Int
)


data class DoorsPageDto(
    val content: List<DoorDto>,
    val page: Int,
    val size: Int,
    val totalElements: Int,
    val totalPages: Int,
    val last: Boolean
)