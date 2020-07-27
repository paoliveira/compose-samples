/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.jetsnack.ui.components

import androidx.compose.animation.animate
import androidx.compose.foundation.background
import androidx.compose.foundation.drawBorder
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.state
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.drawWithContent
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.HorizontalGradient
import androidx.compose.ui.graphics.LinearGradient
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.unit.dp

fun Modifier.horizontalGradientBackground(
    colors: List<Color>
) = gradientBackground(colors) { gradientColors, size ->
    HorizontalGradient(
        colors = gradientColors,
        startX = 0f,
        endX = size.width
    )
}

fun Modifier.diagonalGradientTint(
    colors: List<Color>,
    blendMode: BlendMode
) = gradientTint(colors, blendMode) { gradientColors, size ->
    LinearGradient(
        colors = gradientColors,
        startX = 0f,
        startY = 0f,
        endX = size.width,
        endY = size.height
    )
}

fun Modifier.gradientBackground(
    colors: List<Color>,
    brushProvider: (List<Color>, Size) -> LinearGradient
): Modifier = composed {
    var size by state { Size.zero }
    val gradient = remember(colors, size) { brushProvider(colors, size) }
    drawWithContent {
        size = this.size
        drawRect(brush = gradient)
        drawContent()
    }
}

fun Modifier.gradientTint(
    colors: List<Color>,
    blendMode: BlendMode,
    brushProvider: (List<Color>, Size) -> LinearGradient
) = composed {
    var size by state { Size.zero }
    val gradient = remember(colors, size) { brushProvider(colors, size) }
    drawWithContent {
        drawContent()
        size = this.size
        drawRect(
            brush = gradient,
            blendMode = blendMode
        )
    }
}

fun Modifier.offsetGradientBackground(
    colors: List<Color>,
    width: Float,
    offset: Float = 0f
) = background(
    HorizontalGradient(
        colors,
        startX = -offset,
        endX = width - offset,
        tileMode = TileMode.Mirror
    )
)

fun Modifier.gradientBorder(
    colors: List<Color>,
    shape: Shape
) = drawBorder(
    size = 2.dp,
    shape = shape,
    brush = LinearGradient(
        colors = colors,
        startX = 0f,
        startY = 0f,
        endX = 111f,
        endY = 59f
    )
)

fun Modifier.fadeInGradientBorder(
    showBorder: Boolean,
    colors: List<Color>,
    shape: Shape
) = composed {
    val animatedColors = List(colors.size) { i ->
        animate(if (showBorder) colors[i] else colors[i].copy(alpha = 0f))
    }
    gradientBorder(
        colors = animatedColors,
        shape = shape
    )
}
