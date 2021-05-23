package com.github.belyakovleonid.core.presentation

import java.text.DecimalFormat

private const val WEIGHT_FORMATTER_PATTERN = "#.#"

fun getWeightString(weight: Float): String {
    return DecimalFormat(WEIGHT_FORMATTER_PATTERN).format(weight)
}