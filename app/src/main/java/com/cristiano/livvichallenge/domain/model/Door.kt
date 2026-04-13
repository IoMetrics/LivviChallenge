package com.cristiano.livvichallenge.domain.model


data class Door(
    val id: Int,
    val name: String,
    val address: String,
    val battery: Int,
    val serial: String,
    val lockMac: String
)