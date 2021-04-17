package io.github.staakk.randomcity.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.util.*

@Entity(tableName = "city")
data class City(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val color: Int,
    val createdAt: LocalDateTime,
    @Embedded
    val coordinate: Coordinate
)