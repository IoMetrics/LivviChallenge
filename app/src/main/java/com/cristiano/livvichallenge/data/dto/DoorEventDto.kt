
package com.cristiano.livvichallenge.data.dto

data class DoorEventDto(
    val id: Int,
    val logType: String,
    val logNumber: Int,
    val eventTimestamp: String,
    val additionalData: List<DoorEventAdditionalDataDto>
)

data class DoorEventAdditionalDataDto(
    val parameterName: String,
    val hexValue: String,
    val parsedValue: String
)
data class DoorEventsPageDto(
    val content: List<DoorEventDto>,
    val page: Int,
    val size: Int,
    val totalElements: Int,
    val totalPages: Int,
    val last: Boolean
)

