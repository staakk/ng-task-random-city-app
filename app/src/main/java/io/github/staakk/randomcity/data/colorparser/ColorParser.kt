package io.github.staakk.randomcity.data.colorparser

import androidx.annotation.ColorInt

interface ColorParser {

    @ColorInt
    fun parse(color: String): Int
}