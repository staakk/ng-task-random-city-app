package io.github.staakk.randomcity.data

import android.graphics.Color
import timber.log.Timber

object ColorParser {

    private val colorMap = mapOf(
        "Yellow" to Color.YELLOW,
        "Green" to Color.GREEN,
        "Blue" to Color.BLUE,
        "Red" to Color.RED,
        "Black" to Color.BLACK,
        "White" to Color.WHITE
    )

    fun parse(color: String) = colorMap[color] ?: run {
        Timber.w("Color $color not found.")
        Color.GREEN
    }
}