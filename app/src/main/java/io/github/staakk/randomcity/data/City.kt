package io.github.staakk.randomcity.data

import java.time.LocalDateTime

data class City(
    val name: String,
    val color: String = "",
    val createdAt: LocalDateTime = LocalDateTime.now()
)