package io.github.staakk.randomcity.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.util.*

@Entity(tableName = "city")
data class City(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val color: String = "",
    val createdAt: LocalDateTime = LocalDateTime.now()
)