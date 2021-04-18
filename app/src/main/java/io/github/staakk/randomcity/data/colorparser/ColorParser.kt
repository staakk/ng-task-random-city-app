package io.github.staakk.randomcity.data.colorparser

import androidx.annotation.ColorInt

interface ColorParser {

    /**
     * Converts [color] into an integer representing this color.
     */
    @ColorInt
    fun parse(color: String): Int
}