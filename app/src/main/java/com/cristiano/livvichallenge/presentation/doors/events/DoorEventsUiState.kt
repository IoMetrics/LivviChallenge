
package com.cristiano.livvichallenge.presentation.doors.events

import com.cristiano.livvichallenge.domain.model.DoorEvent

data class DoorEventsUiState(
    val isLoading: Boolean = false,
    val events: List<DoorEvent> = emptyList(),
    val errorMessage: String? = null,
    val shouldLogout: Boolean = false
)