package com.cristiano.livvichallenge.data.mapper

import com.cristiano.livvichallenge.core.common.toBrazilianDateTime
import com.cristiano.livvichallenge.data.dto.DoorDto
import com.cristiano.livvichallenge.data.dto.DoorEventAdditionalDataDto
import com.cristiano.livvichallenge.data.dto.DoorEventDto
import com.cristiano.livvichallenge.domain.model.Door
import com.cristiano.livvichallenge.domain.model.DoorEvent
import com.cristiano.livvichallenge.domain.model.DoorEventAdditionalData

fun DoorDto.toDomain(): Door {
    return Door(
        id = id,
        name = name,
        address = address,
        battery = battery,
        serial = serial,
        lockMac = lockMac
    )
}



fun DoorEventDto.toDomain(): DoorEvent {
    return DoorEvent(
        id = id,
        logType = logType,
        logNumber = logNumber,
        eventTimestamp = eventTimestamp.toBrazilianDateTime(),
        additionalData = additionalData.map { it.toDomain() }
    )
}

fun DoorEventAdditionalDataDto.toDomain(): DoorEventAdditionalData {
    return DoorEventAdditionalData(
        parameterName = parameterName,
        hexValue = hexValue,
        parsedValue = parsedValue
    )
}