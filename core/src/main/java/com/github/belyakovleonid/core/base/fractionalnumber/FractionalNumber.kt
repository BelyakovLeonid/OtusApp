package com.github.belyakovleonid.core.base.fractionalnumber

import com.github.belyakovleonid.core.base.fractionalnumber.FractionalNumber.Companion.DEFAULT_FRACT_PART_SIZE
import kotlin.math.pow
import kotlin.math.roundToInt

private const val DECIMAL_BASE = 10F

data class FractionalNumber(
    val intPart: Int,
    val fractPart: Int,
    val fractPartSize: Int = DEFAULT_FRACT_PART_SIZE
) {

    operator fun plus(other: FractionalNumber): FractionalNumber {
        return (this.toFloat() + other.toFloat()).toFractional()
    }

    companion object {
        const val DEFAULT_FRACT_PART_SIZE = 1

        val ZERO = FractionalNumber(0, 0)
    }
}

fun Float.toFractional(fractPartSize: Int = DEFAULT_FRACT_PART_SIZE): FractionalNumber {
    val intPart = this.toInt()
    val fractPart = ((this - intPart) * DECIMAL_BASE.pow(fractPartSize)).roundToInt()

    return FractionalNumber(
        intPart = intPart,
        fractPart = fractPart,
        fractPartSize = fractPartSize
    )
}

fun FractionalNumber.toFloat(): Float {
    return intPart + fractPart / DECIMAL_BASE.pow(fractPartSize)
}