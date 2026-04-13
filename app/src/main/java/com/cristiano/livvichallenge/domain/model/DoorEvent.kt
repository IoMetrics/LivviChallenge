package com.cristiano.livvichallenge.domain.model

data class DoorEvent(
    val id: Int,
    val logType: String,
    val logNumber: Int,
    val eventTimestamp: String,
    val additionalData: List<DoorEventAdditionalData>
)

data class DoorEventAdditionalData(
    val parameterName: String,
    val hexValue: String,
    val parsedValue: String
)