package com.github.belyakovleonid.core.presentation

import kotlin.math.PI

private const val HALF_CIRCLE = 180

fun gradsToRadians(grads: Float): Float = grads / HALF_CIRCLE * PI.toFloat()

fun radiansToGrads(radians: Float): Float = radians / PI.toFloat() * HALF_CIRCLE