package io.github.staakk.randomcity.util

/**
 * Returns color brightness as a float between 0 and 1.
 */
val Int.brightness : Float
    get() {
        val r: Float = (this shr 16 and 0xff) / 255.0f
        val g: Float = (this shr 8 and 0xff) / 255.0f
        val b: Float = (this and 0xff) / 255.0f

        return (r + g + b) / 3
    }