package io.github.staakk.randomcity.data.colorparser

import android.content.Context
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.res.ResourcesCompat
import io.github.staakk.randomcity.R
import timber.log.Timber

class ResourcesColorParser(
    private val context: Context
) : ColorParser {

    private val colorMap = mapOf(
        "Yellow" to R.color.yellow_700,
        "Green" to R.color.green_700,
        "Blue" to R.color.blue_700,
        "Red" to R.color.red_700,
        "Black" to R.color.black,
        "White" to R.color.white
    )

    @ColorInt
    override fun parse(color: String) = ResourcesCompat.getColor(
        context.resources,
        getColor(color),
        context.theme
    )

    @ColorRes
    private fun getColor(color: String) = colorMap[color] ?: run {
        Timber.w("Color $color not found.")
        R.color.blue_700
    }
}